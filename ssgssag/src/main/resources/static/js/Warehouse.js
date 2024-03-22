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
          let row = `<tr>
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
    let button = $(this);
    let warehouseCd = button.data('warehouse-cd');
    openWarehouseModal(button[0], warehouseCd);
  });
});

function openWarehouseModal(button,warehouseCd) {
  let warehouseNm = button.dataset.warehouseNm;
  let warehouseType = button.dataset.warehouseType;
  let warehouseLoc = button.dataset.warehouseLoc;
  let pkMemberSeq = button.dataset.pkMemberSeq;


  $('#warehouseDetail .modal-body input[id="WarehouseCd"]').val(warehouseCd);
  $('#warehouseDetail .modal-body input[id="WarehouseNm"]').val(warehouseNm);
  $('#warehouseDetail .modal-body input[id="WarehouseType"]').val(warehouseType);
  $('#warehouseDetail .modal-body input[id="WarehouseLoc"]').val(warehouseLoc);
  $('#warehouseDetail .modal-body input[id="MemberSeq"]').val(pkMemberSeq);
  document.getElementById('hiddenWarehouseCd').value = warehouseCd;

  $.ajax({
    url: '/warehouse/' + warehouseCd + '/zones',
    type: 'GET',
    success: function(data) {
      let modalBody = $('#warehouseDetail .modal-body');
      let tbody = modalBody.find('table tbody');
      tbody.empty();
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

function addZone() {

  const zoneCode = document.getElementById('zoneCode').value;
  const warehouseCd = document.getElementById('hiddenWarehouseCd').value;
  const zoneName = document.getElementById('zoneName').value;

  const data = {
    vZoneCd: zoneCode,
    vWarehouseCd: warehouseCd,
    vZoneNm: zoneName
  };

  console.log("zoneCode:", zoneCode);
  console.log("warehouseCd:", warehouseCd);
  console.log("zoneName:", zoneName);

  $.ajax({
    type: "POST",
    url: "/warehouse/addZone",
    data: data,
    success: function(response) {
      console.log("Zone added successfully");
      $('#warehouseDetail').modal('hide');
    },
    error: function(error) {
      console.log("Error adding zone", error);
    }
  });
}