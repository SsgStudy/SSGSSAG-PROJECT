$(document).ready(function () {
  $('.input-daterange-datepicker').datepicker({
    dateFormat: 'mm/dd/yy'
  });
  $('.list-form').submit(function (e) {
    e.preventDefault();

    let dateRange = $('[name="daterange"]').val();
    let startDate = dateRange.split(' - ')[0];
    let endDate = dateRange.split(' - ')[1];
    let warehouseCd = $('[data-target="#warehouseSearchInputBox"]').val();
    let supplierNm = $('[data-target="#purchaserSearchInputBox"]').val();
    let status;
    switch ($('#inputState').val()) {
      case '신규':
        status = 'NEW_INVENTORY';
        break;
      case '미입고':
        status = 'UN_INVENTORIED';
        break;
      default:
        status = null;
    }

    startDate = startDate ? inputFormatDate(startDate) : null;
    endDate = endDate ? inputFormatDate(endDate) : null;

    warehouseCd = warehouseCd.trim() ? warehouseCd : null;
    supplierNm = supplierNm.trim() ? supplierNm : null;

    const payload = {
      startDate: startDate,
      endDate: endDate,
      warehouseCd: warehouseCd === "" ? null : warehouseCd,
      supplierNm: supplierNm === "" ? null : supplierNm,
      status: status
    };

    $.ajax({
      url: '/incoming/list/filter',
      type: 'POST',
      contentType: 'application/x-www-form-urlencoded',
      data: $.param(payload),
      success: function (data) {
        let tableBody = $('.zero-configuration tbody');
        tableBody.empty();
        console.log(tableBody)

        $.each(data, function (index, item) {
          let row = $('<tr>').click(function () {
            fetchIncomingDetails(item.pkIncomingProductSeq);
          });
          row.append($('<td>').text(index + 1)); // No
          row.append($('<td>').text(item.pkIncomingProductSeq)); // 번호
          row.append($('<td>').text(item.vincomingProductStatus)); // 입고 상태
          row.append($('<td>').text(item.vproductCd)); // 상품 코드
          const dateOnly = item.dtIncomingProductDate.split('T')[0];
          row.append($('<td>').text(dateOnly));
          row.append($('<td>').text(item.vincomingProductType)); // 종류
          row.append($('<td>').text(item.vincomingProductSupplierNm)); // 매입처
          row.append($('<td>').text(item.nincomingProductCnt)); // 수량
          row.append($('<td>').text(item.nincomingProductPrice)); // 단가
          row.append($('<td>').text(item.vwarehouseCd)); // 창고
          row.append($('<td>').text(item.vzoneCd)); // 구역

          tableBody.append(row);
        });
      },
      error: function (xhr, status, error) {
        console.error('Error:', error);
      }
    });
  });
  $('.confirm-form').submit(function (e) {
    e.preventDefault();

    let dateRange = $('[name="daterange"]').val();
    let startDate = dateRange.split(' - ')[0];
    let endDate = dateRange.split(' - ')[1];
    let warehouseCd = $('[data-target="#warehouseSearchInputBox"]').val();
    let supplierNm = $('[data-target="#purchaserSearchInputBox"]').val();
    let status;
    switch ($('#inputState').val()) {
      case '신규':
        status = 'NEW_INVENTORY';
        break;
      case '미입고':
        status = 'UN_INVENTORIED';
        break;
      default:
        status = null;
    }

    startDate = startDate ? inputFormatDate(startDate) : null;
    endDate = endDate ? inputFormatDate(endDate) : null;

    warehouseCd = warehouseCd.trim() ? warehouseCd : null;
    supplierNm = supplierNm.trim() ? supplierNm : null;

    const payload = {
      startDate: startDate,
      endDate: endDate,
      warehouseCd: warehouseCd === "" ? null : warehouseCd,
      supplierNm: supplierNm === "" ? null : supplierNm,
      status: status
    };

    $.ajax({
      url: '/incoming/list/unconfirm-filter',
      type: 'POST',
      contentType: 'application/x-www-form-urlencoded',
      data: $.param(payload),
      success: function (data) {
        let tableBody = $('.zero-configuration tbody');
        tableBody.empty();
        console.log(tableBody)

        $.each(data, function (index, item) {
          let row = $('<tr>').click(function () {
            fetchIncomingDetails(item.pkIncomingProductSeq);
          });
          row.append($('<td>').text(index + 1)); // No
          row.append($('<td>').text(item.pkIncomingProductSeq)); // 번호
          let checkbox = $('<input>').attr({
            type: 'checkbox',
            id: 'checkbox' + index,
            'data-pk-incoming-product-seq': item.pkIncomingProductSeq
          });
          let checkboxContainer = $('<div>').addClass('checkbox m-t-40').append(checkbox);
          row.append($('<td>').append(checkboxContainer));
          row.append($('<td>').text(item.vincomingProductStatus)); // 입고 상태
          row.append($('<td>').text(item.vproductCd)); // 상품 코드
          const dateOnly = item.dtIncomingProductDate.split('T')[0];
          row.append($('<td>').text(dateOnly));
          row.append($('<td>').text(item.vincomingProductType)); // 종류
          row.append($('<td>').text(item.vincomingProductSupplierNm)); // 매입처
          row.append($('<td>').text(item.nincomingProductCnt)); // 수량
          row.append($('<td>').text(item.nincomingProductPrice)); // 단가
          row.append($('<td>').text(item.vwarehouseCd)); // 창고
          row.append($('<td>').text(item.vzoneCd)); // 구역

          tableBody.append(row);
        });
      },
      error: function (xhr, status, error) {
        console.error('Error:', error);
      }
    });
  });

  $('.reset-button').click(function () {
    $('#filter-form').each(function () {
      this.reset();
    });
  });

  $("#purchaserSearchInputBoxTrigger").click(function() {
    $.ajax({
      url: '/incoming/supplier',
      type: 'GET',
      dataType: 'json',
      success: function(data) {
        let tableBody = $("#purchaserSearchInputBox .table-responsive tbody");
        tableBody.empty();
        $.each(data, function(index, item) {
          let row = "<tr>" +
              "<td>" + (index + 1) + "</td>" +
              "<td>" + item.vincomingProductSupplierNm + "</td>" +
              "</tr>";
          tableBody.append(row);
        });
      },
      error: function(xhr, status, error) {
        alert("An error occurred: " + error);
      }
    });
  });

  $("#purchaserSearchInputBox .table-responsive tbody").on('click', 'tr', function() {
    let supplierName = $(this).find('td:nth-child(2)').text();
    $('#purchaserSearchInputBoxTrigger').val(supplierName);
    $('#purchaserSearchInputBox').modal('hide');
  });
});

function fetchIncomingDetails(pkIncomingProductSeq) {
  $.ajax({
    url: '/incoming/details/' + pkIncomingProductSeq,
    type: 'GET',
    success: function (data) {
      let detailsTable = $('#detailsTable tbody');
      detailsTable.empty();

      let totalPrice = data.nincomingProductCnt * data.nincomingProductPrice;

      let newRow = `<tr>
          <td>${data.vproductCd}</td>
          <td>${data.vproductNm}</td>
          <td>${data.vproductStatus || '상태 정보 없음'}</td>
          <td>${data.nproductPrice || 0}</td>
          <td>${data.nincomingProductPrice}</td>
          <td>${data.nincomingProductCnt}</td>
          <td>${totalPrice}</td>
          <td>${data.vproductBrand}</td>
          <td>${data.vproductOrigin}</td>
          <td>${data.vproductManufactor}</td>
          <td>${data.dproductManufactorDate.split('T')[0]}</td>
        </tr>`;

      detailsTable.append(newRow);
    },
    error: function (error) {
      console.log('Error:', error);
    }
  });
}

function confirmSelectedProducts() {
  let selectedProductIds = [];
  $('input[type="checkbox"]:checked').each(function () {
    selectedProductIds.push($(this).data('pk-incoming-product-seq'));
  });
  console.log(selectedProductIds);
  $.ajax({
    url: '/incoming/confirm',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(selectedProductIds),
    success: function (response) {
      alert('입고 목록이 확정되었습니다.');
      window.location.reload();
    },
    error: function (xhr, status, error) {
      alert('오류 발생: ' + error);
    }
  });
}

function formatDate(dateString) {
  if (!dateString) {
    return '';
  }
  let date = new Date(dateString);
  let year = date.getFullYear();
  let month = ('0' + (date.getMonth() + 1)).slice(-2);
  let day = ('0' + date.getDate()).slice(-2);
  return year + '-' + month + '-' + day;
}

function printPage() {
  window.print();
}

function inputFormatDate(input) {
  let datePart = input.match(/\d+/g),
      year = datePart[2],
      month = datePart[0], day = datePart[1];
  if (year.length === 2) {
    year = "20" + year;
  }

  return [year, month, day].join('-');
}
