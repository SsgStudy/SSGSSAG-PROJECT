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
    $("#modify-pw-2").css("display", "block");
    validForm.pw = false;
  } else {
    $("#modify-pw-2").hide();
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
  profileImg: null,
  imgStatus: null
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
    url: '/signup',
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

// 프로필 이미지 변경
$('#new-img').click(function() {
  $('#file').click();
});

// 이미지 업로드
$('#file').change(function(event) {
  let input = this;

  if (input.files && input.files[0]) {
    if (!input.files[0].type.match("image/*")) {
      toastr.warning("이미지 파일만 업로드할 수 있습니다.");
      return;
    }

    // 파일 압축
    compressImage(input.files[0]).then(compressedFile => {
      if (compressedFile) {

        const reader = new FileReader();
        reader.onload = function(e) {
          $('.profile-image-preview').removeAttr('src').attr('src', e.target.result);
        };
        reader.readAsDataURL(compressedFile);

        if (member.imgStatus !== 'user')
          member.imgStatus = 'user';

      }
    }).catch(error => {
      console.error("An error occurred:", error);
    });
  }
});

// 기본 이미지 변경
function changeDefaultImg() {
  $('.profile-image-preview').attr('src', '/images/profile/default-img.png');
  $('.user-img img').removeClass('default-img');
  member.imgStatus = 'default';
}

// 이미지 압축
async function compressImage(inputFile) {
  try {
    const options = {
      maxSizeMB: 0.5,
      maxWidthOrHeight: 1000,
    };
    return await imageCompression(inputFile, options);

  } catch (error) {
    console.error("이미지 압축 오류", error);
    return null;
  }
}

// 비밀번호 수정
function modifyPassword() {
  let oldPw = $('#current-pw').val();
  let newPw = $('#modify-pw-1').val();

  let form = {
    "oldPw": oldPw,
    "newPw": newPw
  }

  $.ajax({
    url: '/member/password',
    method : 'PATCH',
    contentType:'application/json',
    data : JSON.stringify(form),
    success: function (resp){
      toastr.success('비밀번호가 성공적으로 변경되었습니다.');
    },
    error: function (e) {
      toastr.error('비밀번호 변경 실패! 다시 시도하세요');
    }
  })
}

// 회원 프로필 변경
function modifyMemberInfo() {
  let memberEmail = $('#vEmail').val();
  const formData = new FormData();

  if (member.imgStatus === 'user') {
    let fileInput = $('#file')[0];
    formData.append("bProfilePic", fileInput.files[0], fileInput.name);
  }
  else {
    formData.append("bProfilePic", new Blob(), "empty.jpg");
  }
  formData.append("vEmail", memberEmail);

  $.ajax({
    url: '/member/info',
    type: 'PATCH',
    processData: false,
    contentType: false,
    data: formData,
    success: function (resp) {
      toastr.success(resp);
      setTimeout(() => {
        window.location.href='/'
      }, 1500);
    },
    error: function (xhr, status, error) {
      toastr.error(xhr.responseText);
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
