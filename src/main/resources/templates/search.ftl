<#import "parts/common.ftl" as c>


<@c.page>
<div class="container">
    <div class="row">
        <div class="col-sm-8 col-md-8 mt-5">

            <h3 class="mt-5 mb-5">Search result:</h3>

             <#list newslist as news>

                 <div class="card mb-3">
                     <img class="card-img-top" src="${news.urlToImage?ifExists}">
                     <div class="card-body">
                         <h5 class="card-title"><a target="_blank" href="/selected_news/${news.title}">${news.title?ifExists}</a></h5>
                         <p class="card-text">${news.description?ifExists}</p>
                         <p class="card-text"><small class="text-muted"><a href="${news.url?ifExists}">Source</a></small></p>
                         <p class="card-text"><small class="text-muted">${news.author?ifExists}</small></p>
                         <p class="card-text"><small class="text-muted">${news.publishedAt?substring(0, 10)} ${news.publishedAt?substring(11, 19)}</small></p>
                     </div>
                 </div>

             <#else>
                    <h5>No news by your query...</h5>
             </#list>

        </div><!--End col-sm-md-->
    </div><!--End row-->
</div><#--End container-->
</@c.page>