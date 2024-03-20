function fetchIncomingDetails(pkIncomingProductSeq) {
  $.ajax({
    url: '/incoming/details/' + pkIncomingProductSeq,
    type: 'GET',
    success: function(data) {
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
    error: function(error) {
      console.log('Error:', error);
    }
  });
}

function confirmSelectedProducts() {
  let selectedProductIds = [];
  $('input[type="checkbox"]:checked').each(function() {
    selectedProductIds.push($(this).data('pk-incoming-product-seq'));
  });
  console.log(selectedProductIds);
  $.ajax({
    url: '/incoming/confirm',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(selectedProductIds),
    success: function(response) {
      alert('입고 목록이 확정되었습니다.');
      window.location.reload();
    },
    error: function(xhr, status, error) {
      alert('오류 발생: ' + error);
    }
  });
}

function formatDate(dateString) {
  if (!dateString) return '';
  let date = new Date(dateString);
  let year = date.getFullYear();
  let month = ('0' + (date.getMonth() + 1)).slice(-2);
  let day = ('0' + date.getDate()).slice(-2);
  return year + '-' + month + '-' + day;
}

function printPage() {
  window.print();
}
