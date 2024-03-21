$(function () {
  $('.passwordEye i').on('click', function () {
    $('input').toggleClass('active');

    if ($('input').hasClass('active')) {
      $(this).attr('class', "fa fa-eye fa-lg")
      .prev('input').attr('type', "text");
    } else {
      $(this).attr('class', "fa fa-eye-slash fa-lg")
      .prev('input').attr('type', 'password');
    }
  });
});

//아이디 중복 확인 버튼 클릭 시 alert창 생성
$(document).ready(function () {
  $("#alertButton").click(function () {
    alert("중복된 아이디입니다.\n다른 아이디를 입력해주세요");
  });
});

function passwordCheck() {
  var pwd1 = document.getElementById('vMemberPw').value;
  var pwd2 = document.getElementById('pwd').value;

  if(pwd1 !== pwd2) {
    document.getElementById('pwdCheck').style.display = "inline";
  }else {
    document.getElementById('pwdCheck').style.display = 'none';
  }

}