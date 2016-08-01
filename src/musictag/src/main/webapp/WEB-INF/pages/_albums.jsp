<%@ page language="java" pageEncoding="UTF-8"%>
<div class='panel panel-default'>
  <div class='panel-heading'>
    <!-- Nav tabs -->
    <ul class='nav nav-album pull-right' role='tablist'>
      <li class='pull-right' role='presentation'>
        <a aria-controls='album-frames' class='btn btn-default' data-toggle='tab' href='#album-frames' role='tab'>
          <span class='glyphicon glyphicon-th-large'></span>
        </a>
      </li>
      <li class='pull-right'>
        &nbsp
      </li>
      <li class='active pull-right' role='presentation'>
        <a aria-controls='album-table' class='btn btn-default' data-toggle='tab' href='#album-table' role='tab'>
          <span class='glyphicon glyphicon-th-list'></span>
        </a>
      </li>
      <li class='pull-right'>
        &nbsp
      </li>
      <li class='pull-right'>
        <form class='form-inline' data-albums-order=''>
          <form-group>
            <label>Order by:</label>
            <select class='form-control'>
              <option data-direction='asc' data-order-by='name' selected>Name(A-Z)</option>
              <option data-direction='desc' data-order-by='name'>Name(Z-A)</option>
              <option data-direction='asc' data-order-by='date'>Date(0-9)</option>
              <option data-direction='desc' data-order-by='date'>Date(9-0)</option>
            </select>
          </form-group>
        </form>
      </li>
    </ul>
    <h4>Albums</h4>
  </div>
  <div class='tab-content' data-direction='asc' data-order-by='name' data-pagination='#pagination' data-per-page='8' data-window='5' id='albums'>
    <div class='tab-pane fade in active' id='album-table' role='tabpanel'>
      <div class='panel-body'>
        <table class='table table-hover' data-release-groups=''>
          <thead>
            <tr>
              <th>Cover & Title</th>
              <th></th>
              <th>First release date</th>
            </tr>
          </thead>
          <tbody></tbody>
        </table>
      </div>
    </div>
    <div class='tab-pane fade' id='album-frames' role='tabpanel'>
      <div class='panel-body row'></div>
    </div>
    <div class='text-center' id='pagination'></div>
  </div>
  <div class='modal fade' id='releases-modal' role='dialog' tabindex='-1'>
    <div class='modal-dialog'>
      <div class='modal-content'>
        <div class='modal-header'>
          <button aria-label='Close' class='close' data-dismiss='modal' type='button'>
            <span aria-hidden='true'>×</span>
          </button>
          <h4 class='modal-title'>Choose one version to browse</h4>
        </div>
        <div class='modal-body'>
          <table class='table table-hover'>
            <thead>
              <th>#</th>
              <th>Title</th>
              <th>Status</th>
              <th>Country</th>
              <th>Date</th>
            </thead>
            <tbody></tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  <div class='modal fade' id='release-group-modal' role='dialog' tabindex='-1'>
    <div class='modal-dialog'>
      <div class='modal-content'>
        <div class='modal-header'>
          <button aria-label='Close' class='close' data-dismiss='modal' type='button'>
            <span aria-hidden='true'>×</span>
          </button>
          <h4 class='modal-title'>No further information</h4>
        </div>
        <div class='modal-body'>
          <span class='glyphicon glyphicon-exclamation-sign'></span>
          Sorry, there is no further information about this album.
        </div>
      </div>
    </div>
  </div>
</div>
