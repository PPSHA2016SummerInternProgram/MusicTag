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
        <span class='avatar-name' data-artist-overview-name=''>&nbsp;</span>
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
</header>
