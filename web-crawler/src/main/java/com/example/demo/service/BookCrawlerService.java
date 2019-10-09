package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.handler.BatchHandler;
import com.example.demo.handler.GetIdHandler;
import com.example.demo.handler.GetNameHandler;
import com.example.demo.handler.Handler;
import com.example.demo.model.*;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Service
public class BookCrawlerService {

    private CategoryMapper categoryMapper;
    private CategoryChildCategoryMapper categoryChildCategoryMapper;
    private SeriesMapper seriesMapper;
    private OriginalAuthorMapper originalAuthorMapper;
    private BookMapper bookMapper;
    private BookOriginalAuthorMapper bookOriginalAuthorMapper;
    private TranslatorAuthorMapper translatorAuthorMapper;
    private BookTranslatorAuthorMapper bookTranslatorAuthorMapper;
    private BooktagMapper booktagMapper;
    private BookBooktagMapper bookBooktagMapper;
    private PublishInfoMapper publishInfoMapper;
    private AlsoLikeMapper alsoLikeMapper;
    private CatalogMapper catalogMapper;
    private AlsoLikeBookMapper alsoLikeBookMapper;

    private static final String NAME = "name";
    private static final String CONFIRM_LIST_HAS_ELE = "qaz1qaz1";

    //使用Jackson的JsonNode要注意
    //1.get path findxxx 这几个方法的差别

    //arrayNode!=null

    //获得字符串是用asText
    //get可以拿到null 或者字符串"null"

    //拿到级次节点

    //toString asText

    private <T> void returnIdList(List<T> result,List<Integer> idList,List<String> name2,List<String> unique, Handler handler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        if(result!=null){
            for(T o:result){
                idList.add(getIdHandler.returnId(o));
                name2.add(getNameHandler.returnName(o));
            }
            unique.removeAll(name2);
            unique.remove(CONFIRM_LIST_HAS_ELE);
            for(String name:unique){
                idList.add(handler.insertReturnIdOrFindReturnId(idList, name));
            }
        }
    }



    //TODO:
    //能不能把上面这个方法也改成是批量的
    //主要是上面这个insertReturnIdOrFindReturnId
    //也就是GetCategoryId
    //和GetAuthorIds
    //getBooktagIds

    //先批量查
    //做差集
    //再一个一个插入 或者批量插

    //TODO:
    //10月9日
    // 上面的todo 基本完成
    //还要做的
    //1.测试新加的方法
    //2.删除重复代码 getIdsFromArrayNode+getIdsFromArrayNodeCore+returnIdList 和 getIdsFromArrayNodeForGetAuthorIds之间的重复
    //差异主要是这两个
    /*
    List<String> names = new ArrayList<>(nameStrings);
List<String> unique = new ArrayList<>(new HashSet<>(names));
     */
    //3.
    /*
    重构

对于arrayNode
会判断!=null isArray() 之后还会遍历
要不要合并在一起
     */


    /*//这个是对的 下面两个方法测试过这个就可以方法就可以删除掉
    private <T> List<Integer>  getIdsFromArrayNodeCore(JsonNode arrayNode, Handler handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        if(arrayNode!=null) {//这里可能有问题
            List<Integer> idList = new ArrayList<>(10);
            if (arrayNode.isArray()) {
                List<String> names = new ArrayList<>(10);
                for (JsonNode node : arrayNode) {
                    names.add(node.get(NAME).asText());
                }
                names.add(CONFIRM_LIST_HAS_ELE);
                List<T> result = batchHandler.multi(names);
                List<String> name2 = new ArrayList<>(10);
                returnIdList(result,idList,name2,names,handler,getIdHandler,getNameHandler);
                return idList;
            }
        }
        return null;
    }
    */

    //10月9日添加 还未测试
    private <T> List<Integer>  getIdsFromArrayNodeCoreCore(JsonNode arrayNode, List<String> names,Handler handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        if(arrayNode!=null) {//这里可能有问题
            List<Integer> idList = new ArrayList<>(10);
            if (arrayNode.isArray()) {
                for (JsonNode node : arrayNode) {
                    names.add(node.get(NAME).asText().trim());
                }
                names.add(CONFIRM_LIST_HAS_ELE);
                List<String> unique = new ArrayList<>(new HashSet<>(names));
                //System.out.println(unique);
                List<T> result = batchHandler.multi(unique);
                List<String> name2 = new ArrayList<>(10);
                returnIdList(result,idList,name2,unique,handler,getIdHandler,getNameHandler);
                return idList;
            }
        }
        return null;
    }

    //10月9日添加 还未测试
    private <T> List<Integer>  getIdsFromArrayNodeCore(JsonNode arrayNode, Handler handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        return getIdsFromArrayNodeCoreCore(arrayNode,new ArrayList<>(),handler,batchHandler,getIdHandler,getNameHandler);
    }

    public <T> List<Integer> getIdsFromArrayNode(JsonNode jsonNode, String nodeName, Handler handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        JsonNode arrayNode = jsonNode.get(nodeName);
        return getIdsFromArrayNodeCore(arrayNode,handler,batchHandler,getIdHandler,getNameHandler);
    }

    //10月9日添加 还未测试 有三个mapper文件 还没有写sql
    /*
    originalAuthorMapper.insertBatchIgnoreIfExist
    translatorAuthorMapper.insertBatchIgnoreIfExist
    booktagMapper.insertBatchIgnoreIfExist
    */
    public <T> List<Integer> getIdsFromArrayNodeCore2(JsonNode arrayNode, List<String> names, Handler<T> handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        if (arrayNode != null) {//这里可能有问题
            List<Integer> idList = new ArrayList<>(10);
            if (arrayNode.isArray()) {
                for (JsonNode node : arrayNode) {
                    names.add(node.get(NAME).asText().trim());
                }
                names.add(CONFIRM_LIST_HAS_ELE);
                List<String> unique = new ArrayList<>(new HashSet<>(names));
                //System.out.println(unique);
                List<T> result = batchHandler.multi(unique);//批量查
                List<String> name2 = new ArrayList<>(10);

                for(T t:result){
                    name2.add(getNameHandler.returnName(t));
                    idList.add(getIdHandler.returnId(t));
                }
                unique.removeAll(name2);
                unique.remove(CONFIRM_LIST_HAS_ELE);

                List<T> insertList = new ArrayList<>(10);
                for(String name:unique){
                    //往insertList加
                    insertList.add(handler.createObjectFromName(name));
                }

                if(!insertList.isEmpty()){
                    //批量插入或忽略
                    handler.insertBatchIgnoreIfExist(insertList);

                    //批量查
                    result = batchHandler.multi(unique);
                    for(T t:result){
                        idList.add(getIdHandler.returnId(t));
                    }
                }

                return idList;
            }
        }
        return null;
    }

    //10月9日添加 还未测试 有三个mapper文件 还没有写sql
    /*
    originalAuthorMapper.insertBatchIgnoreIfExist
    translatorAuthorMapper.insertBatchIgnoreIfExist
    booktagMapper.insertBatchIgnoreIfExist
    */
    public <T> List<Integer> getIdsFromArrayNode2(JsonNode jsonNode, String nodeName, Handler<T> handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        JsonNode arrayNode = jsonNode.get(nodeName);
        List<String> names = new ArrayList<>();
        return getIdsFromArrayNodeCore2(arrayNode,names,handler,batchHandler,getIdHandler,getNameHandler);
    }


    public <T> List<Integer> getIdsFromArrayNodeFromGetCategoryIds(JsonNode jsonNode, String nodeName, Handler handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        JsonNode arrayNode0 = jsonNode.get(nodeName);
        for (JsonNode arrayNode:arrayNode0) {
            return getIdsFromArrayNodeCore(arrayNode,handler,batchHandler,getIdHandler,getNameHandler);
        }
        return null;
    }


    //从这里开始有数据库操作

    public List<Integer> getCategoryIds(JsonNode jsonNode){
         return getIdsFromArrayNodeFromGetCategoryIds(jsonNode,"categories", (ids, name) ->{
            //Category category = categoryMapper.selectByName(name);
            //if(category==null){

                Category category = new Category();
                category.setCategoryName(name);
                category.setGrade(ids.size()+1);
                //categoryMapper.insertAndReturnId(category);//这里id一定是1 要换成从category中拿
                //Integer id = category.getCategoryId();

             Integer id;
             try{
                 categoryMapper.insertAndReturnId(category);
                 id = category.getCategoryId();
             }catch (Exception ex){
                 category = categoryMapper.selectByName(name);
                 id = category.getCategoryId();
                 System.out.println("categoryId "+id);
             }

                //System.out.println(id);
                if(ids.size()>0) {
                    CategoryChildCategory categoryChildCategory = new CategoryChildCategory();
                    categoryChildCategory.setCategoryId(ids.get(ids.size() - 1));
                    categoryChildCategory.setChildCategoryId(id);
                    //categoryChildCategoryMapper.insertSelective(categoryChildCategory);

                    categoryChildCategoryMapper.insertIgnoreIfExist(categoryChildCategory);
                }
                return id;

            //}
            //return category.getCategoryId();
        },names -> categoryMapper.selectIn(names),category -> category.getCategoryId(),category -> category.getCategoryName());
    }

    public Integer getSeriesId(JsonNode jsonNode){
        JsonNode bookCollectionName = jsonNode.get("bookCollectionName");
        if(bookCollectionName!=null){
            if(!bookCollectionName.equals(NullNode.getInstance())) {
                String name = bookCollectionName.asText();
                Series series = seriesMapper.selectByName(name);
                if (series == null) {
                    series = new Series();
                    series.setSeriesName(name);
                    //seriesMapper.insertAndReturnId(series);//插入或忽略 或者 插入或更新 但是id怎么处理

                    try{
                        seriesMapper.insertAndReturnId(series);
                    }catch (Exception ex){
                        series = seriesMapper.selectByName(name);
                        System.out.println("seriesId "+series.getSeriesId());
                        System.out.println(ex);
                    }

                        //System.out.println(series.getSeriesId());
                    return series.getSeriesId();
                }
                return series.getSeriesId();
            }
        }
        return null;
   }


    public <T> List<Integer> getIdsFromArrayNodeForGetAuthorIds(List<String> nameStrings,JsonNode jsonNode, String nodeName, Handler handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler){
        JsonNode arrayNode = jsonNode.get(nodeName);
        List<String> names = new ArrayList<>(nameStrings);
        return getIdsFromArrayNodeCoreCore(arrayNode,names,handler,batchHandler,getIdHandler,getNameHandler);
    }

    //10月9日添加 还未测试 有三个mapper文件 还没有写sql
    /*
originalAuthorMapper.insertBatchIgnoreIfExist
translatorAuthorMapper.insertBatchIgnoreIfExist
booktagMapper.insertBatchIgnoreIfExist
     */
    public <T> List<Integer> getIdsFromArrayNodeForGetAuthorIds2(List<String> nameStrings,JsonNode jsonNode, String nodeName, Handler<T> handler, BatchHandler<T> batchHandler, GetIdHandler<T> getIdHandler, GetNameHandler<T> getNameHandler) {
        JsonNode arrayNode = jsonNode.get(nodeName);
        List<String> names = new ArrayList<>(nameStrings);
        return getIdsFromArrayNodeCore2(arrayNode,names,handler,batchHandler,getIdHandler,getNameHandler);
    }


    public String getAuthorIntro(String[] a,String name){
        StringBuilder stringBuilder = new StringBuilder();

        for(String p:a){
            if(p.contains(name)){
                stringBuilder.append("\r\n").append(p);
            }
        }
        return stringBuilder.toString();
    }

    public List<Integer> getAuthorIds(JsonNode jsonNode,String type){

        String authorInfo = childNode(jsonNode,"briefIntro","authorInfo");
        String[] paragraph = new String[0];
        if(authorInfo!=null){
            paragraph = authorInfo.split("\\r\\n");
        }

        String[] a = paragraph;

        if(type.equals("Author")) {
            List<String> nameStrings = new ArrayList<>(10);
            String authorNameString = jsonNode.get("authorNameString").asText();
            if(authorNameString.matches("[\u4e00-\u9fa5\\s\\p{Zs}]+")){
                String[] nameStringArray = authorNameString.split(" ");
                for (String n : nameStringArray) {
                    if(n.length()>0) {
                        nameStrings.add(n.trim());
                    }
                }
            }else {
                String[] nameStringArray = authorNameString.split(",");
                for (String n : nameStringArray) {
                    nameStrings.add(n.trim());
                }
            }

            //System.out.println(nameStrings);

            return getIdsFromArrayNodeForGetAuthorIds(nameStrings,jsonNode.get("contributor"),type,(ids, name) -> {
                OriginalAuthor originalAuthor = new OriginalAuthor();
                originalAuthor.setAuthorName(name);

                originalAuthor.setOriginalAuthorIntro(getAuthorIntro(a,name));

                //originalAuthorMapper.insertAndReturnId(originalAuthor);

                //return originalAuthor.getOriginalAuthorId();

                try{
                    originalAuthorMapper.insertAndReturnId(originalAuthor);
                    return originalAuthor.getOriginalAuthorId();
                }catch (Exception ex){
                    System.out.println(ex);
                    /*
                    List<String> names = new ArrayList<>(10);
                    names.add(name);
                    Integer authorId = originalAuthorMapper.selectIn(names).get(0).getOriginalAuthorId();
                    */
                    Integer authorId = originalAuthorMapper.selectByName(name).getOriginalAuthorId();
                    System.out.println("authorId "+authorId);
                    return authorId;
                }
            },names -> originalAuthorMapper.selectIn(names),originalAuthor -> originalAuthor.getOriginalAuthorId(),originalAuthor -> originalAuthor.getAuthorName());
        }else{
            List<String> nameStrings = new ArrayList<>(10);
            String authorNameString = jsonNode.get("translatorNameString").asText();
            if(authorNameString.matches("[\u4e00-\u9fa5\\s\\p{Zs}]+")){
                String[] nameStringArray = authorNameString.split(" ");
                for (String n : nameStringArray) {
                    if(n.length()>0) {
                        nameStrings.add(n.trim());
                    }
                }
            }
            return getIdsFromArrayNodeForGetAuthorIds(nameStrings,jsonNode.get("contributor"),type,(ids, name) -> {
                TranslatorAuthor translatorAuthor = new TranslatorAuthor();
                translatorAuthor.setAuthorName(name);

                translatorAuthor.setTranslatorAuthorIntro(getAuthorIntro(a,name));

                //translatorAuthorMapper.insertAndReturnId(translatorAuthor);
                //return translatorAuthor.getTranslatorAuthorId();

                try{
                    translatorAuthorMapper.insertAndReturnId(translatorAuthor);
                    return translatorAuthor.getTranslatorAuthorId();
                }catch (Exception ex){
                    System.out.println(ex);
                    /*
                    List<String> names = new ArrayList<>(10);
                    names.add(name);
                    Integer translatorAuthorId = translatorAuthorMapper.selectIn(names).get(0).getTranslatorAuthorId();
                    */
                    Integer translatorAuthorId = translatorAuthorMapper.selectByName(name).getTranslatorAuthorId();
                    System.out.println("translatorAuthorId "+translatorAuthorId);
                    return translatorAuthorId;
                }


            },names -> translatorAuthorMapper.selectIn(names),translatorAuthor -> translatorAuthor.getTranslatorAuthorId(),translatorAuthor -> translatorAuthor.getAuthorName());
        }
    }

    //10月9日添加 还未测试 有三个mapper文件 还没有写sql
    /*
originalAuthorMapper.insertBatchIgnoreIfExist
translatorAuthorMapper.insertBatchIgnoreIfExist
booktagMapper.insertBatchIgnoreIfExist
     */
    public List<Integer> getAuthorIds2(JsonNode jsonNode,String type){

        String authorInfo = childNode(jsonNode,"briefIntro","authorInfo");
        String[] paragraph = new String[0];
        if(authorInfo!=null){
            paragraph = authorInfo.split("\\r\\n");
        }

        String[] a = paragraph;

        if(type.equals("Author")) {
            List<String> nameStrings = new ArrayList<>(10);
            String authorNameString = jsonNode.get("authorNameString").asText();
            if(authorNameString.matches("[\u4e00-\u9fa5\\s\\p{Zs}]+")){
                String[] nameStringArray = authorNameString.split(" ");
                for (String n : nameStringArray) {
                    if(n.length()>0) {
                        nameStrings.add(n.trim());
                    }
                }
            }else {
                String[] nameStringArray = authorNameString.split(",");
                for (String n : nameStringArray) {
                    nameStrings.add(n.trim());
                }
            }

            //System.out.println(nameStrings);

            return getIdsFromArrayNodeForGetAuthorIds2(nameStrings, jsonNode.get("contributor"), type, new Handler<OriginalAuthor>() {
                @Override
                public Integer insertReturnIdOrFindReturnId(List<Integer> ids, String name) {
                    return null;
                }

                @Override
                public OriginalAuthor createObjectFromName(String name) {
                    OriginalAuthor originalAuthor = new OriginalAuthor();
                    originalAuthor.setAuthorName(name);

                    originalAuthor.setOriginalAuthorIntro(getAuthorIntro(a,name));
                    return originalAuthor;
                }

                @Override
                public void insertBatchIgnoreIfExist(List<OriginalAuthor> list) {
                    originalAuthorMapper.insertBatchIgnoreIfExist(list);
                }
            }, names -> originalAuthorMapper.selectIn(names), originalAuthor -> originalAuthor.getOriginalAuthorId(), originalAuthor -> originalAuthor.getAuthorName());
        }else{
            List<String> nameStrings = new ArrayList<>(10);
            String authorNameString = jsonNode.get("translatorNameString").asText();
            if(authorNameString.matches("[\u4e00-\u9fa5\\s\\p{Zs}]+")){
                String[] nameStringArray = authorNameString.split(" ");
                for (String n : nameStringArray) {
                    if(n.length()>0) {
                        nameStrings.add(n.trim());
                    }
                }
            }
            return getIdsFromArrayNodeForGetAuthorIds2(nameStrings, jsonNode.get("contributor"), type, new Handler<TranslatorAuthor>() {
                @Override
                public Integer insertReturnIdOrFindReturnId(List ids, String name) {
                    return null;
                }

                @Override
                public TranslatorAuthor createObjectFromName(String name) {
                    TranslatorAuthor translatorAuthor = new TranslatorAuthor();
                    translatorAuthor.setAuthorName(name);

                    translatorAuthor.setTranslatorAuthorIntro(getAuthorIntro(a,name));
                    return translatorAuthor;
                }

                @Override
                public void insertBatchIgnoreIfExist(List<TranslatorAuthor> list) {
                    translatorAuthorMapper.insertBatchIgnoreIfExist(list);
                }
            }, names -> translatorAuthorMapper.selectIn(names), translatorAuthor -> translatorAuthor.getTranslatorAuthorId(), translatorAuthor -> translatorAuthor.getAuthorName());
        }
    }

    public List<Integer> getBooktagIds(JsonNode jsonNode){
        return getIdsFromArrayNode(jsonNode,"tags",(ids, name) -> {

            //疑问：之前为什么把//A处注释掉 保留着是可以的 getSeriesId那里就保存着
            //Booktag booktag = booktagMapper.selectByName(name);//A
            //if(booktag==null){//A
                Booktag booktag = new Booktag();
                booktag.setBooktagName(name);
                //booktagMapper.insertAndReturnId(booktag);
                //return booktag.getBooktagId();

                try{
                    booktagMapper.insertAndReturnId(booktag);
                    return booktag.getBooktagId();
                }catch (Exception ex){
                    System.out.println(ex);
                    /*
                    List<String> names = new ArrayList<>(10);
                    names.add(name);
                    Integer booktagId = booktagMapper.selectIn(names).get(0).getBooktagId();
                    */
                    Integer booktagId = booktagMapper.selectByName(name).getBooktagId();
                    System.out.println("booktagId "+booktagId);
                    return booktagId;
                }
            //}//A
            //return booktag.getBooktagId();//A
        },names -> booktagMapper.selectIn(names),booktag -> booktag.getBooktagId(),booktag -> booktag.getBooktagName());
    }

    //10月9日添加 还未测试 有三个mapper文件 还没有写sql
    /*
originalAuthorMapper.insertBatchIgnoreIfExist
translatorAuthorMapper.insertBatchIgnoreIfExist
booktagMapper.insertBatchIgnoreIfExist
     */
    public List<Integer> getBooktagIds2(JsonNode jsonNode){
        return getIdsFromArrayNode2(jsonNode, "tags", new Handler<Booktag>() {
            @Override
            public Integer insertReturnIdOrFindReturnId(List ids, String name) {
                return null;
            }

            @Override
            public Booktag createObjectFromName(String name) {
                Booktag booktag = new Booktag();
                booktag.setBooktagName(name);
                return booktag;
            }

            @Override
            public void insertBatchIgnoreIfExist(List<Booktag> list) {
                booktagMapper.insertBatchIgnoreIfExist(list);
            }
        }, names -> booktagMapper.selectIn(names), booktag -> booktag.getBooktagId(), booktag -> booktag.getBooktagName());
    }


    public void insertCatalog(JsonNode jsonNode,Integer bookId){
        if(catalogMapper.selectCountByBookId(bookId)>0){
            return;
        }
        JsonNode contentTable = jsonNode.get("contentTable");
        if(contentTable!=null && !contentTable.equals(NullNode.getInstance())){
            String[] chapters = contentTable.asText().split("</br>\\n\\n");
            List<Catalog> catalogs = new ArrayList<>(10);
            for(String chapter:chapters){
                Catalog catalog = new Catalog();
                catalog.setBookId(bookId);
                catalog.setChapterIntro(chapter);
                catalogs.add(catalog);
            }
            catalogMapper.insertBatch(catalogs);
        }

        JsonNode ebook = jsonNode.get("ebook");
        if(ebook!=null && !ebook.equals(NullNode.getInstance())){

            JsonNode arrayNode = ebook.get("chapters");
            if(arrayNode!=null && !arrayNode.equals(NullNode.getInstance())) {//这里可能有问题
                if (arrayNode.isArray()) {
                    List<Catalog> catalogs = new ArrayList<>(10);
                    for (JsonNode node : arrayNode) {
                        Catalog catalog = new Catalog();
                        catalog.setBookId(bookId);
                        catalog.setChapterIntro(node.get("subject").asText());

                        if(node.get("isFree").asBoolean()){
                            catalog.setChapterLink("http://www.ituring.com.cn/book/tupubarticle/"+node.get("id").asText());
                        }
                        catalogs.add(catalog);
                    }
                    if(!catalogs.isEmpty()){
                        catalogMapper.insertBatch(catalogs);
                    }
               }
            }
        }
    }

    //这个方法有批量查 再批量插 再批量查的例子
    public Integer insertAlsoLike(JsonNode jsonNode) {
        JsonNode arrayNode = jsonNode.get("relatedBooks");
        if (arrayNode != null) {
            if (arrayNode.isArray()) {

                List<String> names = new ArrayList<>(10);
                for(JsonNode node:arrayNode){
                    names.add(node.get(NAME).asText().trim());
                }
                names.add(CONFIRM_LIST_HAS_ELE);
                List<Book> books1 = bookMapper.selectIn(names);
                List<String> names2 = new ArrayList<>(10);
                for(Book book:books1){
                    names2.add(book.getBookTitle());
                }
                names.removeAll(names2);
                names.remove(CONFIRM_LIST_HAS_ELE);
                List<Book> books = new ArrayList<>(10);
                for (String name:names){
                    Book book = new Book();
                    book.setBookTitle(name);
                    books.add(book);
                }
                if(!books.isEmpty()) {
                    //bookMapper.insertBatch(books);//这里多线程环境下要去重 防止重复插入

                    bookMapper.insertBatchIgnoreIfExist(books);
                    books = bookMapper.selectIn(names);
                    System.out.println("bookId "+books.get(0).getBookId());
                }
                books.addAll(books1);
                if(!books.isEmpty()){
                    AlsoLike alsoLike = new AlsoLike();
                    alsoLike.setAlsoLikeName((alsoLikeMapper.selectMaxId() + 1) + "");
                    alsoLikeMapper.insertAndReturnId(alsoLike);

                    List<AlsoLikeBook> alsoLikeBooks = new ArrayList<>(10);
                    for(Book book:books){
                        AlsoLikeBook alsoLikeBook = new AlsoLikeBook();
                        alsoLikeBook.setAlsoLikeId(alsoLike.getAlsoLikeId());
                        if(book.getBookId()==null){
                            System.out.println("bookId is null");
                        }
                        alsoLikeBook.setBookId(book.getBookId());
                        alsoLikeBooks.add(alsoLikeBook);
                    }
                    alsoLikeBookMapper.insertBatch(alsoLikeBooks);
                    return alsoLike.getAlsoLikeId();
                }
            }
        }
        return null;
    }

    public void insertPublishInfo(JsonNode jsonNode,Book book){
        if(publishInfoMapper.selectCountByBookId(book.getBookId())>0){
            return;
        }

        PublishInfo publishInfo = new PublishInfo();
        publishInfo.setBookId(book.getBookId());
        publishInfo.setBookTitle(book.getBookTitle());
        Series series = seriesMapper.selectByPrimaryKey(book.getSeriesId());
        if(series!=null) {
            publishInfo.setSeriesName(series.getSeriesName());
        }
        publishInfo.setBookDate(book.getBookDate());

        publishInfo.setOriginalBookNumber(childNode(jsonNode,"originalBookInfo","originalBookIsbn"));
        publishInfo.setAmazonLink(childNode(jsonNode,"originalBookInfo","originalBookAmazonUrl"));
        publishInfo.setBookSize(childNode(jsonNode,"paperEditionInfo","pageSizeName"));
        String pageNum = childNode(jsonNode,"paperEditionInfo","pageNumber");
        if(pageNum!=null) {
            publishInfo.setPageNum(Integer.parseInt(pageNum));
        }

        publishInfo.setQuestionContact(childNode(jsonNode,"deputyEditor",NAME));

        publishInfo.setBookNumber(jsonNode.get("isbn").asText());
        publishInfo.setPrice(book.getPaperOriginalPrice());

        publishInfo.setBookStatus(book.getLabel());
        publishInfo.setOriginalTitle(childNode(jsonNode,"originalBookInfo","originalBookName"));
        publishInfo.setPrint(childNode(jsonNode,"paperEditionInfo","bookPrintName"));
        publishInfoMapper.insertSelective(publishInfo);
    }

    public void insertOriginalPrice(JsonNode jsonNode,Book book){
        JsonNode arrayNode = jsonNode.get("bookEditionPrices");
        if(arrayNode!=null) {//这里可能有问题
            if (arrayNode.isArray()) {
                for (JsonNode node : arrayNode) {
                    //BigDecimal price = node.get("name").decimalValue();
                    BigDecimal price = new BigDecimal(node.get(NAME).asText());
                    //System.out.println(price);
                    String stringPrice = node.get("key").asText();
                    if(stringPrice.equals("Paper")){
                        book.setPaperOriginalPrice(price);
                    }
                    if(stringPrice.equals("Ebook")){
                        book.setEbookPrice(price);
                    }
                }
            }
        }
    }

    public void insertSalePrice(JsonNode jsonNode,Book book) {
        JsonNode arrayNode = jsonNode.get("salesInfos");
        if (arrayNode != null) {//这里可能有问题
            if (arrayNode.isArray()) {
                //BigDecimal price = arrayNode.get(0).get("discountPrice").decimalValue();
                BigDecimal price = new BigDecimal(arrayNode.get(0).get("discountPrice").asText());
                book.setPaperSalePrice(price);
                //BigDecimal p = arrayNode.get(1).get("discountPrice").decimalValue();
                BigDecimal p = new BigDecimal(arrayNode.get(1).get("discountPrice").asText());
                book.setEbookSalePrice(p);
            }
        }
    }


    public void insertBook(JsonNode jsonNode){
        boolean alreadyExist = true;
        Book book = null;
        book = bookMapper.selectByBookTitle(jsonNode.get(NAME).asText().trim());
        if(book==null){
            /*
            if(jsonNode.get("name").asText().contains("C++必知必会")){
                System.out.println(jsonNode.get("name").asText());
            }
            */
            book = new Book();
            alreadyExist = false;
        }

        book.setBookVote(jsonNode.get("voteCount").asInt());
        book.setBookEnterNum(jsonNode.get("viewCount").asInt());
        String bookDateString = jsonNode.get("publishDate").asText();
        if(bookDateString!=null && !bookDateString.equals("null")) {
            Date bookDate = null;
            try {
                //System.out.println(jsonNode.get("publishDate").asText());
                bookDate = DateUtil.get().parse(bookDateString);
                //System.out.println(bookDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            book.setBookDate(bookDate);
        }

        List<Integer> categoryIds = getCategoryIds(jsonNode);
        //System.out.println(categoryIds);
        if(categoryIds!=null) {
            book.setCategoryId(categoryIds.get(categoryIds.size() - 1));

            if(categoryIds.size()>0) {
                Integer firstGradeId = categoryIds.get(0);
                if (firstGradeId != null) {
                    book.setFirstGradeId(firstGradeId);
                }
            }

            if(categoryIds.size()>1) {
                Integer secondGradeId = categoryIds.get(1);
                if (secondGradeId != null) {
                    book.setSecondGradeId(secondGradeId);
                }
            }

            if(categoryIds.size()>2) {
                Integer thirdGradeId = categoryIds.get(2);
                if (thirdGradeId != null) {
                    book.setThirdGradeId(thirdGradeId);
                }
            }
        }

        Integer seriesId = getSeriesId(jsonNode);
        book.setSeriesId(seriesId);//这里要不要先判空再set

        book.setFeature(childNode(jsonNode,"briefIntro","highlight"));

        book.setBookIntro(childNode(jsonNode,"briefIntro","abstract"));

        book.setAmazonLink(childNode(jsonNode,"externalSalesInfo","saleAmazonUrl"));

        book.setDangdangLink(childNode(jsonNode,"externalSalesInfo","saleDangdangUrl"));

        book.setJdLink(childNode(jsonNode,"externalSalesInfo","saleJingdongUrl"));

        String imageLink = jsonNode.get("coverKey").asText();
        book.setImageLink("http://file.ituring.com.cn/SmallCover/"+imageLink);
        book.setBigImageLink("http://file.ituring.com.cn/ScreenShow/"+imageLink);
        book.setBookTitle(jsonNode.get(NAME).asText().trim());
        book.setWithBookDownload(childNode(jsonNode,"resources","url"));

        JsonNode bookStatus = jsonNode.get("bookStatus");
        if(bookStatus!=null){
            if(bookStatus.asText().equals("70")){
                book.setLabel("暂时缺货");
            }else if(bookStatus.asText().equals("80")){
                book.setLabel("终止销售");
            }else if(bookStatus.asText().equals("60")){
                book.setLabel("上市销售");
            }else if(bookStatus.asText().equals("46")){
                book.setLabel("正在排版");
            }else if(bookStatus.asText().equals("43")){
                book.setLabel("正在编辑");
            }else if(bookStatus.asText().equals("20")){
                book.setLabel("正在翻译");
            }else if(bookStatus.asText().equals("50")){
                book.setLabel("正在印刷");
            }else if(bookStatus.asText().equals("10")){
                book.setLabel("诚招译者");
            }else{
                System.out.println("missing "+jsonNode.get("id")+bookStatus.asText());
            }
        }

        insertOriginalPrice(jsonNode,book);

        insertSalePrice(jsonNode,book);

        if(book.getAlsoLikeId()==null) {
            book.setAlsoLikeId(insertAlsoLike(jsonNode));
        }

        if(jsonNode.get("onSaleEdition").asInt()>=4){
            book.setIsEbook(1);
        }

        JsonNode sampleFileKey = jsonNode.get("sampleFileKey");
        if(sampleFileKey!=null){
            book.setSampleDownload("http://www.ituring.com.cn/file/samplefile/"+jsonNode.get("encrypt").asText());//这里可能有问题
        }

        if(jsonNode.get("isGift").asBoolean()&&jsonNode.get("hasGiftBook").asBoolean()){
            book.setEnableGift(1);
        }else{
            book.setEnableGift(0);
        }

        Integer bookId;
        if(alreadyExist){
            bookMapper.updateByPrimaryKeySelective(book);
            bookId = book.getBookId();
        }else {
            bookMapper.insertAndReturnId(book);
         bookId = book.getBookId();
        }

        //下面需要用到bookId

        if(bookOriginalAuthorMapper.selectCountByBookId(bookId)<=0) {
            List<Integer> originalAuthorIds = getAuthorIds(jsonNode, "Author");
            if (originalAuthorIds != null) {
                List<BookOriginalAuthor> bookOriginalAuthors = new ArrayList<>(10);
                for (Integer originalAuthorId : originalAuthorIds) {
                    BookOriginalAuthor bookOriginalAuthor = new BookOriginalAuthor();
                    bookOriginalAuthor.setBookId(bookId);
                    bookOriginalAuthor.setOriginalAuthorId(originalAuthorId);
                    bookOriginalAuthors.add(bookOriginalAuthor);
                }
                if(!bookOriginalAuthors.isEmpty()){
                    bookOriginalAuthorMapper.insertBatch(bookOriginalAuthors);//中间表 在多线程环境下 不用去重 避免重复插入
                }
            }
        }

        if(bookTranslatorAuthorMapper.selectCountByBookId(bookId)<=0) {
            List<Integer> translatorAuthorIds = getAuthorIds(jsonNode, "Translator");
            if (translatorAuthorIds != null) {
                List<BookTranslatorAuthor> bookTranslatorAuthors = new ArrayList<>(10);
                for (Integer translatorAuthorId : translatorAuthorIds) {
                    BookTranslatorAuthor bookTranslatorAuthor = new BookTranslatorAuthor();
                    bookTranslatorAuthor.setBookId(bookId);
                    bookTranslatorAuthor.setTranslatorAuthorId(translatorAuthorId);
                    bookTranslatorAuthors.add(bookTranslatorAuthor);
                }
                if(!bookTranslatorAuthors.isEmpty()){
                    bookTranslatorAuthorMapper.insertBatch(bookTranslatorAuthors);
                }
            }
        }

        if(bookBooktagMapper.selectCountByBookId(bookId)<=0) {
            List<Integer> booktagIds = getBooktagIds(jsonNode);
            if (booktagIds != null) {
                List<BookBooktag> bookBooktags = new ArrayList<>(10);
                for (Integer booktagId : booktagIds) {
                    BookBooktag bookBooktag = new BookBooktag();
                    bookBooktag.setBookId(bookId);
                    bookBooktag.setBooktagId(booktagId);
                    bookBooktags.add(bookBooktag);
                }
                if(!bookBooktags.isEmpty()){
                    bookBooktagMapper.insertBatch(bookBooktags);
                }
            }
        }

        insertCatalog(jsonNode,bookId);

        insertPublishInfo(jsonNode,book);

    }

    private String childNode(JsonNode jsonNode,String f,String c){
        JsonNode briefIntro = jsonNode.get(f);

        String feature = null;

        if(briefIntro!=null){
            JsonNode highlight =  briefIntro.get(c);
            if(highlight!=null){
                if(!highlight.equals(NullNode.getInstance())) {
                    feature = highlight.asText();
                }
            }
        }
        return feature;
    }

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Autowired
    public void setCategoryChildCategoryMapper(CategoryChildCategoryMapper categoryChildCategoryMapper) {
        this.categoryChildCategoryMapper = categoryChildCategoryMapper;
    }

    @Autowired
    public void setSeriesMapper(SeriesMapper seriesMapper) {
        this.seriesMapper = seriesMapper;
    }

    @Autowired
    public void setOriginalAuthorMapper(OriginalAuthorMapper originalAuthorMapper) {
        this.originalAuthorMapper = originalAuthorMapper;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Autowired
    public void setBookOriginalAuthorMapper(BookOriginalAuthorMapper bookOriginalAuthorMapper) {
        this.bookOriginalAuthorMapper = bookOriginalAuthorMapper;
    }

    @Autowired
    public void setTranslatorAuthorMapper(TranslatorAuthorMapper translatorAuthorMapper) {
        this.translatorAuthorMapper = translatorAuthorMapper;
    }

    @Autowired
    public void setBookTranslatorAuthorMapper(BookTranslatorAuthorMapper bookTranslatorAuthorMapper) {
        this.bookTranslatorAuthorMapper = bookTranslatorAuthorMapper;
    }

    @Autowired
    public void setBooktagMapper(BooktagMapper booktagMapper) {
        this.booktagMapper = booktagMapper;
    }

    @Autowired
    public void setBookBooktagMapper(BookBooktagMapper bookBooktagMapper) {
        this.bookBooktagMapper = bookBooktagMapper;
    }

    @Autowired
    public void setPublishInfoMapper(PublishInfoMapper publishInfoMapper) {
        this.publishInfoMapper = publishInfoMapper;
    }

    @Autowired
    public void setAlsoLikeMapper(AlsoLikeMapper alsoLikeMapper) {
        this.alsoLikeMapper = alsoLikeMapper;
    }

    @Autowired
    public void setCatalogMapper(CatalogMapper catalogMapper) {
        this.catalogMapper = catalogMapper;
    }

    @Autowired
    public void setAlsoLikeBookMapper(AlsoLikeBookMapper alsoLikeBookMapper) {
        this.alsoLikeBookMapper = alsoLikeBookMapper;
    }
}
