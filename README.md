# MQ-starter-web-crawler
基于Redis实现的具备ACK特性的消息队列 以及使用该消息队列的一个web爬虫

参考PHP Laravel的消息队列，和《Redis实战》中消息队列的相关内容，使用Redis的Lua脚本编写的消息队列，支持延迟队列和ACK。并编写了一个简易的Spring Boot Starter，方便引入该消息队列。

使用上述消息队列解耦爬虫的数据获取和数据解析，使用基于Netty的AsyncHttpClient用于获取数据，使用Jackson 2的JsonNode类用于数据解析，爬取某图书网站信息。
使用集合运算、批量查询、批量插入和插入或忽略，实现数据入库，并防止重复插入。
