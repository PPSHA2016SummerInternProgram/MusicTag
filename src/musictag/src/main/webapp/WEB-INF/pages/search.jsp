<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta content='text/html; charset=UTF-8' http-equiv='Content-Type'>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/release.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/artist-overview.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/search.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/filter.css" />
    <script src="<%=request.getContextPath()%>/js/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
    <script src="<%=request.getContextPath()%>/js/util.js"></script>
    <script src="<%=request.getContextPath()%>/js/tag_builder.js"></script>
    <script src="<%=request.getContextPath()%>/js/paginator.js"></script>
    <script src="<%=request.getContextPath()%>/js/search.js"></script>
    <script src="<%=request.getContextPath()%>/js/filter.js"></script>
  </head>
  <body>
    <%@ include file="_navbar.html"%>
    <div class='container' style='margin-top:80px;'>
      <div class='row'>
        <div class='col-md-6 col-md-offset-3'>
          <form action='/musictag/search'>
            <div class='input-group' id='search-bar'>
              <input class='form-control' id='search-input' name='key' placeholder='Search for...' type='text'>
              <span class='input-group-btn'>
                <button class='btn btn-default' type='submit'>
                  <i class='glyphicon glyphicon-search'></i>
                </button>
              </span>
            </div>
          </form>
        </div>
      </div>
      <div id='search-summary'>
        <p>&nbsp;</p>
      </div>
      <!-- Nav tabs -->
      <ul class='nav nav-tabs' id='search-tabs' role='tablist'>
        <li role='presentation'>
          <a aria-controls='home' data-toggle='tab' href='#recordings' id='recordings-tab' role='tab'>Recordings</a>
        </li>
        <li role='presentation'>
          <a aria-controls='profile' data-toggle='tab' href='#artists' id='artists-tab' role='tab'>Artists</a>
        </li>
        <li role='presentation'>
          <a aria-controls='messages' data-toggle='tab' href='#releases' id='releases-tab' role='tab'>Albums</a>
        </li>
        <li role='presentation'>
          <a aria-controls='messages' data-toggle='tab' href='#lyrics' id='lyrics-tab' role='tab'>Lyrics</a>
        </li>
      </ul>
      <!-- Tab panes -->
      <div class='tab-content'>
        <div class='tab-pane fade' id='recordings' role='tabpanel'>
          <table class='table table-striped' data-page-param='currPage' data-pagination='#recordings-pagination' data-per-page-param='perPage' data-per-page='24' data-window='10' id='recording-tbl'>
            <thead>
              <th>Name</th>
              <th>Artist(s)</th>
              <th>Related album(s)</th>
              <th>Length</th>
            </thead>
            <tbody></tbody>
          </table>
          <div class='text-center' id='recordings-pagination'></div>
        </div>
        <div class='tab-pane fade' id='artists' role='tabpanel'>
          <button class='btn btn-success' id='apply-filter'>
            Apply filter
          </button>
          <div class='panel panel-default' id='artist-filter'></div>
          <div class='panel panel-default' id='artist-partition'>
            <div class='partition row'>
              <div class='col-md-1'>
                <span class='field'>Range</span>
              </div>
              <div class='col-md-11'>
                <span class='partition-input' data-partition-field='relationship_rank'>
                  <span class='field'>Relationship:</span>
                  <input class='begin form-control' maxlength='2' type='text'>
                  <span>~</span>
                  <input class='end form-control' maxlength='2' type='text'>
                </span>
                <span class='partition-input' data-partition-field='popularity_rank'>
                  <span class='field'>Popularity:</span>
                  <input class='begin form-control' maxlength='2' type='text'>
                  <span>~</span>
                  <input class='end form-control' maxlength='2' type='text'>
                </span>
                <span class='partition-input' data-partition-field='influence_rank'>
                  <span class='field'>Influence:</span>
                  <input class='begin form-control' maxlength='2' type='text'>
                  <span>~</span>
                  <input class='end form-control' maxlength='2' type='text'>
                </span>
                <span class='partition-input' data-partition-field='productivity_rank'>
                  <span class='field'>Productivity:</span>
                  <input class='begin form-control' maxlength='2' type='text'>
                  <span>~</span>
                  <input class='end form-control' maxlength='2' type='text'>
                </span>
                <span class='partition-input' data-partition-field='active_span_rank'>
                  <span class='field'>Active span:</span>
                  <input class='begin form-control' maxlength='2' type='text'>
                  <span>~</span>
                  <input class='end form-control' maxlength='2' type='text'>
                </span>
              </div>
            </div>
          </div>
          <div class='panel panel-default' id='artist-sorter'>
            <div class='sorter row'>
              <div class='col-md-1'>
                <span class='field'>Order by</span>
              </div>
              <div class='col-md-11 sorter-group'>
                <span class='sorter-pair'>
                  <i class='up glyphicon glyphicon-triangle-top' data-sorter='relationship_score'></i>
                  <span class='sorter-name'>Relationship</span>
                  <i class='down glyphicon glyphicon-triangle-bottom' data-sorter='relationship_score'></i>
                </span>
                <span class='sorter-pair'>
                  <i class='up glyphicon glyphicon-triangle-top' data-sorter='popularity_score'></i>
                  <span class='sorter-name'>Popularity</span>
                  <i class='down glyphicon glyphicon-triangle-bottom' data-sorter='popularity_score'></i>
                </span>
                <span class='sorter-pair'>
                  <i class='up glyphicon glyphicon-triangle-top' data-sorter='influence_score'></i>
                  <span class='sorter-name'>Influence</span>
                  <i class='down glyphicon glyphicon-triangle-bottom' data-sorter='influence_score'></i>
                </span>
                <span class='sorter-pair'>
                  <i class='up glyphicon glyphicon-triangle-top' data-sorter='productivity_score'></i>
                  <span class='sorter-name'>Productivity</span>
                  <i class='down glyphicon glyphicon-triangle-bottom' data-sorter='productivity_score'></i>
                </span>
                <span class='sorter-pair'>
                  <i class='up glyphicon glyphicon-triangle-top' data-sorter='active_span_score'></i>
                  <span class='sorter-name'>Active span</span>
                  <i class='down glyphicon glyphicon-triangle-bottom' data-sorter='active_span_score'></i>
                </span>
              </div>
            </div>
          </div>
          <div class='row' data-page-param='currPage' data-pagination='#artist-pagination' data-per-page-param='perPage' data-per-page='24' data-window='10' id='artist-frames'></div>
          <div class='text-center' id='artist-pagination'></div>
        </div>
        <div class='tab-pane fade' id='releases' role='tabpanel'>
          <div class='row' data-page-param='currPage' data-pagination='#release-pagination' data-per-page-param='perPage' data-per-page='24' data-window='10' id='release-frames'></div>
          <div class='text-center' id='release-pagination'></div>
        </div>
        <div class='tab-pane fade' id='lyrics' role='tabpanel'>
          <table class='table table-striped' data-page-param='currPage' data-pagination='#lyrics-pagination' data-per-page-param='perPage' data-per-page='24' data-window='10' id='lyric-tbl'>
            <thead>
              <th>Recording</th>
              <th>Lyric</th>
            </thead>
            <tbody></tbody>
          </table>
          <div class='text-center' id='lyrics-pagination'></div>
        </div>
      </div>
    </div>
  </body>
</html>
