package com.qf.shop_search.Controller;

import com.qf.entity.SolrPage;
import com.qf.entity.goods;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/solr")
public class searchController {
    @Autowired
    SolrClient solrClient;
    @ResponseBody
    @RequestMapping("/addGoods")
    public Boolean addGoods(@RequestBody goods goods){
        System.out.println("id_______________"+goods.getId());
        SolrInputDocument solrDocument = new SolrInputDocument();
        solrDocument.addField("id",goods.getId());
        solrDocument.addField("gtitle",goods.getTitle());
        solrDocument.addField("gprice",goods.getPrice());
        solrDocument.addField("goods_info",goods.getGinfo());
        solrDocument.addField("gimage",goods.getGimage());

        try {
            solrClient.add(solrDocument);
            solrClient.commit();
            System.out.println("调用了");
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @RequestMapping("/queryBySolr")
    public String queryBySolr(String query, Model model, SolrPage<goods> solrPage){
        SolrQuery solrQuery = new SolrQuery();
        if(!"".equals(query.trim())&&query!=null){
            solrQuery.setQuery("goods_info:"+query);
        }else{
            solrQuery.setQuery("*:*");
        }
        solrQuery.setHighlight(true);
        //设置高亮的折叠


        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.addHighlightField("gtitle");
        solrQuery.setHighlightSnippets(2);
        solrQuery.setHighlightFragsize(1);
        //设置分页显示
        solrQuery.setStart((solrPage.getPageNow()-1)*solrPage.getPageSize());
        solrQuery.setRows(solrPage.getPageSize());


        QueryResponse query1 = null;
        ArrayList list = new ArrayList();
        try {
            query1 = solrClient.query(solrQuery);

            SolrDocumentList results = query1.getResults();
            //分页计算总条数
            long numFound = results.getNumFound();
            solrPage.setPageTotal((int) numFound);
            solrPage.setPageCount(solrPage.getPageTotal()%solrPage.getPageSize()==0?solrPage.getPageTotal()/solrPage.getPageSize():(solrPage.getPageTotal()/solrPage.getPageSize()+1));

            Map<String, Map<String, List<String>>> highlighting = query1.getHighlighting();
            for(SolrDocument roleGoods:results){
                goods goods = new goods();
                goods.setId(Integer.parseInt(roleGoods.getFieldValue("id")+""));
                goods.setTitle(roleGoods.getFieldValue("gtitle")+"");
                goods.setGimage(roleGoods.getFieldValue("gimage")+"");
                goods.setPrice(Float.parseFloat(roleGoods.getFieldValue("gprice")+""));
                goods.setGinfo(roleGoods.getFieldValue("goods_info")+"");
                if(highlighting.containsKey(goods.getId()+"")){
                    List<String> titles = highlighting.get(goods.getId()+"").get("gtitle");
                    System.out.println(titles);
                    if(titles != null) {
                        String tit = "";
                        for (String title : titles) {
                                tit = tit+title+"...";
                        }
                        System.out.println("测试高亮关键字:"+tit);
                        goods.setTitle(tit);
                    }
                }
                list.add(goods);
            }
            solrPage.setData(list);
            model.addAttribute("solrPage",solrPage);
            model.addAttribute("query",query);
            System.out.println(solrPage);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "list";
    }

}