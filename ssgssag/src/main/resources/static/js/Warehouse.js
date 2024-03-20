let ready = $(document).ready(function() {

  $("form").submit(function (event) {
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