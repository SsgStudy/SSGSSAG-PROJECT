let ready = $(document).ready(function() {

  $('#searchForm').submit(function (event) {
    event.preventDefault();

    var formData = {
      name: $("input[name='name']").val(),
      location: $("select[name='location']").val(),
      type: $("select[name='type']").val()
    };

    $.ajax({
      type: "POST",
      url: "/warehouse/search",
      data: $.param(formData),
      contentType: 'application/x-www-form-urlencoded',
      success: function (data) {
        let tableBody = $('.zero-configuration tbody');
        tableBody.empty();

        $.each(data, function (i, warehouse) {
          var row = `<tr>
                        <td>${warehouse.vwarehouseCd}</td>
                        <td>${warehouse.vwarehouseNm}</td>
                        <td>${warehouse.swarehouseType}</td>
                        <td>${warehouse.vwarehouseLoc}</td>
                        <td>${warehouse.pkMemberSeq}</td>
                        <td>
                            <button type="button" class="btn btn-info btn-sm open-modal"
                                    data-warehouse-cd="${warehouse.vwarehouseCd}"
                                    data-warehouse-nm="${warehouse.vwarehouseNm}"
                                    data-warehouse-type="${warehouse.swarehouseType}"
                                    data-warehouse-loc="${warehouse.vwarehouseLoc}"
                                    data-pk-member-seq="${warehouse.pkMemberSeq}">
                                구역 보기
                            </button>
                        </td>
                    </tr>`;
          tableBody.append(row);
        });
      }, error: function (error) {
        console.error("Error: ", error);
      }
    });
  });

  $(document).on('click', '.open-modal', function() {
    var button = $(this); // 클릭된 버튼의 jQuery 객체
    var warehouseCd = button.data('warehouse-cd');
    // 여기서 openWarehouseModal 함수를 호출하고 필요한 데이터를 전달합니다.
    openWarehouseModal(button[0], warehouseCd); // button[0]을 통해 DOM 요소 전달
  });
});

function openWarehouseModal(button,warehouseCd) {
  var warehouseNm = button.dataset.warehouseNm;
  var warehouseType = button.dataset.warehouseType;
  var warehouseLoc = button.dataset.warehouseLoc;
  var pkMemberSeq = button.dataset.pkMemberSeq;

  // 모달 내의 입력 필드에 값을 설정
  $('#warehouseDetail .modal-body input[placeholder="${vWarehouseCd}"]').val(warehouseCd);
  $('#warehouseDetail .modal-body input[placeholder="중앙창고"]').val(warehouseNm);
  $('#warehouseDetail .modal-body input[placeholder="일반"]').val(warehouseType);
  $('#warehouseDetail .modal-body input[placeholder="서울시 강남구"]').val(warehouseLoc);
  $('#warehouseDetail .modal-body input[placeholder="MGR001"]').val(pkMemberSeq);

  // 구역 정보 조회 및 표시
  $.ajax({
    url: '/warehouse/' + warehouseCd + '/zones',
    type: 'GET',
    success: function(data) {
      var modalBody = $('#warehouseDetail .modal-body');
      var tbody = modalBody.find('table tbody');
      tbody.empty(); // 기존 행 초기화
      if (data && data.length > 0) {
        $.each(data, function(index, zone) {
          let row = `<tr>
              <td>${index + 1}</td>
              <td>${zone.vzoneCd}</td>
              <td>${zone.vzoneNm}</td>
            </tr>`;
          tbody.append(row);
        });
      } else {
        tbody.append('<tr><td colspan="3">해당 창고에 구역 정보가 없습니다.</td></tr>');
      }
      $('#warehouseDetail').modal('show');
    },
    error: function(error) {
      console.error("Error fetching warehouse zones:", error);
    }
  });
}
