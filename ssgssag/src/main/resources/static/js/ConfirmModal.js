$(document).ready(function () {
  $("#order-register-remove-btn").click(function () {
    swal(
      {
        title: "Confirm",
        text: "삭제하시겠습니까?",
        type: "info",
        showCancelButton: true,
        closeOnConfirm: false,
        showLoaderOnConfirm: true,
      },
      function () {
        setTimeout(function () {
          swal("저장되었습니다.");
        }, 2000);
      }
    );
  });

  $("#order-register-new-btn").click(function () {
    $("#order-register-orderseq-txt").val("00000001"); // 발주번호 생성해서 넣음
  });

  $("#order-register-save-ok-btn").click(function () {
    // ajax
  });

  $("#order-register-remove-ok-btn").click(function () {
    console.log('초기화')
    $("#order-register-form").find("input[type=text], select").val("");
  });

  $("#order-register-confirm-ok-btn").click(function() {
    // ajax
    // 발주 DB 삽입
  });

  $("#order-read-btn").click(function() {
    // ajax
    // 기간에 해당하는 발주 리스트 조회
  })
});
