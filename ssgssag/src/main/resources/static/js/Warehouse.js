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


  $('#sWarehouseTypeSelect').change(function() {

    if ($(this).val() == 'custom') {
      $('#sWarehouseTypeInput').show();
    } else {

      $('#sWarehouseTypeInput').hide().val('');
    }
  }).change();


  $('#warehouseAdd form').submit(function(event) {
    event.preventDefault();


    let isCustomType = $('#sWarehouseTypeSelect').val() === 'custom';
    let warehouseType = isCustomType ? $('#sWarehouseTypeInput').val() : $('#sWarehouseTypeSelect').val();
    let formData = $(this).serializeArray();

    formData = formData.filter(item => item.name !== 'sWarehouseType');
    formData.push({ name: 'sWarehouseType', value: warehouseType });

    $.ajax({
      type: $(this).attr('method'),
      url: $(this).attr('action'),
      data: $.param(formData),
      contentType: 'application/x-www-form-urlencoded',
      success: function(response) {

        console.log("창고 추가 완료");
        $('#warehouseAdd').modal('hide');
      },
      error: function(xhr, status, error) {
        console.error("Error: ", error);
      }
    });
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
  let warehouseCd = document.getElementById('hiddenWarehouseCd').value;
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


let element_wrap = document.getElementById('wrap');
let element_modal_wrap = document.getElementById('modal-wrap');

function foldDaumPostcode() {

  //확인버튼 누르면
  //취소 버튼 누르면
  // iframe을 넣은 element를 안보이게 한다.
  element_wrap.style.display = 'none';
  element_modal_wrap.style.display = 'none';
}

function openAddressCheckModal() {
  // element_address_modal.style.display ='block';
  element_wrap.style.display = 'none';
}

var addrTest = '';
function sample3_execDaumPostcode(userId) {

  console.log("실행");

  // 현재 scroll 위치를 저장해놓는다.
  var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
  new daum.Postcode({
    oncomplete: function(data) {
      // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수
      var extraAddr = ''; // 참고항목 변수

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else { // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
      if(data.userSelectedType === 'R'){
        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
          extraAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가한다.
        if(data.buildingName !== '' && data.apartment === 'Y'){
          extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
        }
        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
        if(extraAddr !== ''){
          extraAddr = ' (' + extraAddr + ')';
        }
        // 조합된 참고항목을 해당 필드에 넣는다.
        addrTest = addr + extraAddr + " ";
        $('#sample3_search').val(addrTest);
        document.getElementById('sample3_search').focus();
        console.log(addrTest);
      } else {
        document.getElementById("sample3_search").value = '';
      }



      // 주소 정보를 해당 필드에 넣는다.

      // 커서를 상세주소 필드로 이동한다.
      // document.getElementById("sample3_detailAddress").focus();

      openAddressCheckModal();

      // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
      document.body.scrollTop = currentScroll;
    },
    // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
    onresize : function(size) {
      element_wrap.style.height = size.height+'px';
    },
    width : '100%',
    height : '100%'
  }).embed(element_wrap);

  // iframe을 넣은 element를 보이게 한다.
  element_wrap.style.display = 'block';
  element_modal_wrap.style.display = 'block';
}

function updateUserAddress () {
//유저 주소의 조합
  const userAddress = $('#sample3_address').val() +' '+ $('#sample3_detailAddress').val() + $('#sample3_extraAddress').val();

  localStorage.setItem('address', userAddress);

}

//취소 버튼 클릭시
$("#address-cancel-button").click(function() {
  element_wrap.style.display = 'none';
  element_modal_wrap.style.display = 'none';
  // element_address_modal.style.display ='none';
});

//확인 버튼 클릭시
$("#address-apply-button").click(function() {
  element_wrap.style.display = 'none';
  element_modal_wrap.style.display = 'none';
  // element_address_modal.style.display ='none';
  updateUserAddress();
});

$('#warehouseAdd').on('hidden.bs.modal', function () {

  $(this).find('form')[0].reset();

  $('#sWarehouseTypeInput').hide().val('');

  $(this).find('select').val('');

});

$('#warehouseDetail').on('hidden.bs.modal', function () {
  // 직접적으로 특정 필드 값 설정
  $('#zoneCode', this).val('');
  $('#zoneName', this).val('');
});