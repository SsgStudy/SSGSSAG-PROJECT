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

  $("#supplierSearchInput").on("input", function() {
    let searchValue = $(this).val().toLowerCase();

    $("#purchaserSearchInputBox .table-responsive tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(searchValue) > -1);
    });
  });


  // 체크박스 클릭 이벤트 핸들러
  $('.checkbox input').change(function() {
    // 모달을 표시하기 전에, 이벤트가 발생한 행을 식별합니다.
    var selectedRow = $(this).closest('tr');
    var incomingDate = selectedRow.find('td:nth-child(6)').text(); // '입고 일자' 열
    var warehouseZone = selectedRow.find('td:last-child').text(); // '구역' 열

    // 모달에 현재 값을 설정합니다.
    $('#incomingDateInput').val(incomingDate); // 입력 필드에 입고 일자 설정
    $('#warehouseZoneSelect').val(warehouseZone); // 선택박스에 구역 설정

    // 모달에서 '확인' 버튼 클릭 시, 변경 사항을 해당 행에 반영합니다.
    $('#confirmButton').off('click').on('click', function() {
      if (!selectedRow.find('.checkbox input').is(':checked')) {
        // 체크박스가 체크 해제되었다면, 모달에서 변경된 값을 반영하지 않습니다.
        return;
      }

      var updatedDate = $('#incomingDateInput').val();
      var updatedZone = $('#warehouseZoneSelect').val();

      // 선택된 행의 '입고 일자'와 '구역' 열을 업데이트합니다.
      selectedRow.find('td:nth-child(6)').text(updatedDate);
      selectedRow.find('td:last-child').text(updatedZone);

      // 모달 숨기기
      $('#incomingDateInputBox').modal('hide');
    });

    // 체크박스 상태에 따라 모달 표시
    if ($(this).is(':checked')) {
      $('#incomingDateInputBox').modal('show');
    } else {
      // 체크가 해제된 경우, 모달을 표시하지 않고 기본 동작만 수행합니다.
      $('#incomingDateInputBox').modal('hide');
    }
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
