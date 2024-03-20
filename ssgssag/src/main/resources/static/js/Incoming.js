$(document).ready(function () {
  $('.input-daterange-datepicker').datepicker({
    dateFormat: 'mm/dd/yy'
  });
  $('form').submit(function (e) {
    e.preventDefault();

    let dateRange = $('[name="daterange"]').val();
    let startDate = dateRange.split(' - ')[0];
    let endDate = dateRange.split(' - ')[1];
    let warehouseCd = $('[data-target="#warehouseSearchInputBox"]').val();
    let supplierNm = $('[data-target="#purchaserSearchInputBox"]').val();
    let status = $('#inputState').val() !== '입고구분 선택' ? $('#inputState').val()
        : null;

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
        console.log(data);
        // 데이터 바인딩 로직
      },
      error: function (xhr, status, error) {
        console.error('Error:', error);
      }
    });
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
          <td>${data.dproductManufactorDate}</td>
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
      year = datePart[2], // get only two digits
      month = datePart[0], day = datePart[1];
  return [year, month, day].join('-');
}