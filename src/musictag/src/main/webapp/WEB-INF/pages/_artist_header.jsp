<%@ page language="java" pageEncoding="UTF-8"%>
<header class='header header-with-avator header-overview'>
  <div class='header-background'></div>
  <div class='container header-inner-wrap'>
    <div class='header-avatar'>
      <div class='avatar-cover img-circle-wrapper'>
        <img data-artist-overview-image=''>
      </div>
    </div>
    <div class='header-nav'>
      <div class='header-title'>
      	<div style="float:left">
        	<span class='avatar-name' data-artist-overview-name=''>&nbsp;</span>
        </div>
        <div  style="float:left; height:0;width:0">
        	<div id="artist-radar-chart" style="margin-top:-30px; width:90px; height:90px;"></div>
        	<div id="big-radar-chart-popover" class="popover fade bottom in" role="tooltip"  style="display:none;">
		    	<div class="arrow" style="bottom:50%"></div>
		    	<h3 id="big-radar-chart-title" style="color:black; font-size:20px" class="popover-title text-center">Scores</h3>
		    	<div class="popover-content" id="big-radar-chart-wrapper">
		    		<div id="big-radar-chart"></div>
		    	</div>
		    </div>
        </div>
        <span style="color:black;line-height:32px">.</span>
        <script src="http://code.highcharts.com/highcharts.js"></script>
		<script src="http://code.highcharts.com/highcharts-more.js"></script>
        <script src="<%=request.getContextPath()%>/js/artist-overview.js"></script>
        <script src="<%=request.getContextPath()%>/js/statistics.js"></script>
        <script>
        	$(function(){
        		<% if(active_tab != 0){ %>
        			getBasicInfoFromServer();
        		<% } %>
        	});
        </script>
      </div>
      <nav class='nav-list'>
        <ul class='nav-list-items'>
          <li class="nav-list-item " ><a href="./" class="<%= active_tab == 0 ?  "active" : ""%>" >Baisc</a></li>
          <li class="nav-list-item " ><a href="relationship" class="<%= active_tab == 1 ?  "active" : ""%>" >Relationship</a></li>
          <li class="nav-list-item " ><a href="popularity" class="<%= active_tab == 2 ?  "active" : ""%>" >Popularity</a></li>
          <li class="nav-list-item " ><a href="influence" class="<%= active_tab == 3 ?  "active" : ""%>" >Influence</a></li>
          <li class="nav-list-item " ><a href="productivity" class="<%= active_tab == 4 ?  "active" : ""%>" >Productivity</a></li>
          <li class="nav-list-item " ><a href="active-span" class="<%= active_tab == 5 ?  "active" : ""%>" >Active Span</a></li>
        </ul>
      </nav>
    </div>
  </div>
<!-- 	<div id="big-radar-chart-modal" class="modal fade"> -->
<!-- 		<div class="modal-dialog" role="document"> -->
<!-- 			<div id="big-radar-chart-modal-content" class="modal-content"> -->
<!-- 				<div class="modal-header"> -->
<!-- 					<button type="button" class="close" data-dismiss="modal" -->
<!-- 						aria-label="Close"> -->
<!-- 						<span aria-hidden="true">&times;</span> -->
<!-- 					</button> -->
<!-- 					<h4 id="big-radar-chart-modal-title" class="modal-title">Modal title</h4> -->
<!-- 				</div> -->
<!-- 				<div class="modal-body"> -->
<!-- 					<div id="big-radar-chart"></div> -->
<!-- 				</div> -->
<!-- 				<div class="modal-footer"> -->
<!-- 					<button type="button" class="btn btn-secondary" -->
<!-- 						data-dismiss="modal">Close</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
</header>
