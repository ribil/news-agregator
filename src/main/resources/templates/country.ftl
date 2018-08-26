<#import "parts/common.ftl" as c>


<@c.page>
<div class="container">
    <div class="row">
        <div class="col-sm-8 col-md-6 mt-5">

            <h5 class="mt-5">Country news:</h5>

            <ul class="list-group mt-3">

                <#list newslist as news>
                    <li class="list-group-item">
                        <p><a style="color: darkslategray" target="_blank" href="/selected_news/${news.id}">${news.title?ifExists}</a></p>
                        <p>${news.country}</p>
                        <p>${news.author?ifExists}</p>
                        <p>${news.description?ifExists}</p>
                        <p><a href="${news.url?ifExists}">url</a></p>
                        <p>${news.publishedAt?ifExists}</p>
                        <p><img width="100px" src="${news.urlToImage?ifExists}"</p>

                    </li>
                <#else>
                    <h5>No news =(</h5>
                </#list>
            </ul>

        </div><!--End col-sm-md-->
    </div><!--End row-->
</div><#--End container-->
</@c.page>