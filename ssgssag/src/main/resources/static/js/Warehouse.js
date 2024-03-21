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
          var row = "<tr>" +
              "<td>" + warehouse.vwarehouseCd + "</td>" +
              "<td>" + warehouse.vwarehouseNm + "</td>" +
              "<td>" + warehouse.swarehouseType + "</td>" +
              "<td>" + warehouse.vwarehouseLoc + "</td>" +
              "<td>" + warehouse.pkMemberSeq + "</td>" +
              "</tr>";
          tableBody.append(row);
        });
      }, error: function (error) {
        console.error("Error: ", error);
      }
    });
  });
})



function openWarehouseModal(warehouseCd) {
  let code = warehouseCd
  $.ajax({
    url: '/warehouse/' + code + '/zones',
    type: 'GET',

    success: function(data) {
      console.log(this.url);
      console.log(data); // 서버로부터 받은 데이터 확인
      let modalBody = $('#warehouseDetail .modal-body');
      let tbody = modalBody.find('table tbody');
      tbody.empty(); // 기존 행 초기화
      console.log(typeof(data));
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