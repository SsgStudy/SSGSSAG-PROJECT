$(document).ready(function() {
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


  $("#checkButton").click(function() {
    checkId();
  });

});

$('.passwordEye').on('input', '#vMemberPw, #pwd', function() {
  let pw1 = $("#vMemberPw").val();
  let pw2 = $("#pwd").val();

  if (pw2===undefined || pw2 === null || pw2 === '') {
    validForm.pw = false;
    return;
  }

  if (pw1 !== pw2) {
    $("#pwdCheck").css("display", "block");
    validForm.pw = false;
  } else {
    $("#pwdCheck").hide();
    validForm.pw = true;
  }
});

$('#form-id').on('input', '#vMemberId', function() {
  validForm.idBtn = false;
});


let member = {
  pkMemberSeq:null,
  vMemberId:null,
  vMemberPw:null,
  vMemberNm:null,
  vMemberAuth:null,
  vEmail:null,
  bProfilePic:null,
  vSocialLoginToken:null,
}

let validForm = {
  email: false,
  idBtn: false,
  pw: false
}



//회원 중복 불러오기
function checkId() {
  let memberId = $('#vMemberId').val().trim();
  if(memberId === '') {
    toastr.warning("아이디를 입력해주세요");
    return
  }

  $.ajax({
    url: '/member/check/id',
    type: 'GET',
    data: {
      vMemberId : memberId
    },
    contentType: 'application/json',
    success: function (response) {
      // 성공 시 처리 로직 작성
      if(response === true) {
        toastr.warning('이미 사용중인 아이디입니다.\n다른 아이디를 입력하세요');
        validForm.idBtn = false;
      } else {
        toastr.success('사용 가능한 아이디입니다.');
        validForm.idBtn = true;
      }
    },
    error: function (xhr, status, error) {
      toastr.error('아이디를 확인할 수 없습니다.');
    }
  })
}

function checkBlank() {

  let memberNm = $('#vMemberNm').val().trim();
  let memberId = $('#vMemberId').val().trim();
  let memberPw = $('#vMemberPw').val().trim();
  let memberEmail = $('#vEmail').val();

  if (memberNm === '' || memberId === '' || memberPw === '' || memberEmail === '') {
    return false;
  }
  return true;
}

function signupRequest() {

  if (!checkEmpty())
    return;

  let member = {
    "vMemberNm" : $('#vMemberNm').val().trim(),
    "vMemberId" : $('#vMemberId').val().trim(),
    "vMemberPw" : $('#vMemberPw').val().trim(),
    "vEmail": $('#vEmail').val()
  }

  $.ajax({
    // url: '/member/signup',
    url: '/member/signup',
    type: 'POST',
    contentType:'application/json',
    data: JSON.stringify(member),
    success: function (response) {
      window.location.href='/';
      toastr.success('회원가입이 완료되었습니다.');
    },
    error: function (xhr, status, error) {
      toastr.error('회원가입에 실패하였습니다. 다시 시도해주세요.');
    }
  });
}

function modifyMemberInfo() {
  let memberId = $('#vMemberId').val();
  let pwd = $('input[name="pwd"]').val();
  let memberEmail = $('input[name="email"]').val();

  $.ajax({
    url: '/member/info',
    type: 'PATCH',
    data: {
      memberId: memberId,
      memberPw: pwd,
      memberEmail: memberEmail,
    },
    success: function (response) {
      window.location.href='/'
      toastr.success("수정 완료")

    },
    error: function (xhr, status, error) {
      toastr.error('수정에 실패하였습니다. 다시 시도해주세요.')
    }
  });
}

function checkEmpty() {
  let isValid = true;
  let $signupForm = $("#signup-form");

  $signupForm.find('input').each(function() {
    let value = $(this).val().trim();

    if ($(this).attr('id') === 'vEmail') {
      validateEmail(value);
    }

    if (value === '') {
      isValid = false;
    }
  });

  if (!isValid) {
    toastr.info('입력하지 않은 값이 있습니다.');
    return false;
  } else if (!validForm.idBtn) {
    toastr.warning('아이디 중복 체크하세요.');
    return false;
  } else if (!validForm.pw) {
    toastr.warning('비밀번호가 다릅니다');
    return false;
  } else if (!validForm.email) {
    toastr.warning('유효하지 않은 이메일 형식입니다.');
    return false;
  }
  else {
    return true;
  }
}



function checkModifyEmpty() {
  let isValid = true;
  let $modifyForm = $("#modify-form");

  $modifyForm.find('input').each(function() {
    let value = $(this).val().trim();

    if ($(this).attr('id') === 'email') {
      validateEmail(value);
    }

    if (value === '') {
      isValid = false;
    }
  });

  if (!isValid) {
    toastr.info('입력하지 않은 값이 있습니다.');
    return false;
  } else if (!validForm.pw) {
    toastr.warning('비밀번호가 다릅니다');
    return false;
  } else if (!validForm.email) {
    toastr.warning('유효하지 않은 이메일 형식입니다.');
    return false;
  }
  else {
    return true;
  }
}





function validateEmail(email) {
  let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (!emailPattern.test(email) && email !== '') {
    validForm.email = false;
  }
  else {
    validForm.email = true;
  }
}

function password() {
  let pwd = $('#vMemberPw').val();
  let pwdCheck = $('#pwd').val();

  if(pwd !==pwdCheck) {
    $('#pwdCheck').show();
    validForm.pw = false;
    return false;
  }else {
    $('#pwdCheck').hide();
    validForm.pw = true;
    return true;
  }
}

function deleteAccount() {

  let memberId = $('#vMemberId').val();

  $.ajax({
    url: `/member/leave`,
    type: 'PATCH',
    data: {memberId: memberId},
    success: function (resp) {
      toastr.success("성공적으로 삭제되었습니다.");
      setTimeout(() => {
        window.location.href = "/";
      }, 1500);
    },
    error: function (error) {
      toastr.error("삭제 실패했습니다.");
    }
  });
}
