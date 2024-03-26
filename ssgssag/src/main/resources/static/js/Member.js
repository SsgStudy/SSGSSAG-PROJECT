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
    console.log("체크버튼 눌림");
    checkId();
  });

  //회원가입 중복
  // $("checkButton").click(function() {
  //
  //   let memberId = $('#vMemberId').val();
  //
  //   $.ajax({
  //     url: '/member/check/id',
  //     type: 'GET',
  //     data: {
  //         vMemberId : memberId
  //     },
  //     contentType: 'application/json',
  //     success: function (response) {
  //       // 성공 시 처리 로직 작성
  //       if(response === true) {
  //         alert('이미 사용중인 아이디입니다.\n 다른 아이디를 입력하세요');
  //       } else {
  //         alert('사용 가능한 아이디입니다.');
  //       }
  //     },
  //     error: function (xhr, status, error) {
  //       console.error('Error:', error);
  //     }
  //   })
  // })
  //


  // 회원 가입 폼 제출 시 Ajax 요청 보내기



  $("#save-modify").submit(function (e) {
    e.preventDefault();

    let memberId = $('#memberId').val();
    let memberPw = $('#memberPw').val();
    let memberName = $('#memberName').val();
    let memberEmail = $('#memberEmail').val();
    let role = $('#memberRole').val();
    let memberAuth;

    console.log("수정된 이름", memberName)

    switch(role) {
      case 'admin':
        memberAuth = 'ADMIN'; break;
      case 'warehouse':
        memberAuth = 'WAREHOUSE_MANAGER'; break;
      case 'operator':
        memberAuth = 'OPERATOR'; break;
      default:
        memberAuth = 'ALL'; break;
    }

    $.ajax({
      url: '/admin/modify-member-info',
      type: 'POST',
      data: {
        memberId: memberId,
        memberPw: memberPw,
        memberName: memberName,
        memberEmail: memberEmail,
        memberAuth: memberAuth
      },
      success: function (response) {
        // 성공 시 처리 로직 작성
        window.location.href='/admin/member-list'
        alert('수정 완료');
      },
      error: function (xhr, status, error) {
        // 에러 처리 로직 작성
        console.log(memberAuth)
        console.error('Error:', error);
        alert('수정에 실패하였습니다. 다시 시도해주세요.');
      }
    });
  });
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


//option 선택 시 입력값 변환
function changeMemberAuth() {
  let role = $('#role').val();
  let memberAuth;

  switch(role) {
    case 'admin':
      memberAuth = 'ADMIN'; break;
    case 'warehouse':
      memberAuth = 'WAREHOUSE_MANAGER'; break;
    case 'operator':
      memberAuth = 'OPERATOR';
    default:
      memberAuth = 'ALL'; break;
  }

  return memberAuth;
}


//회원 중복 불러오기
function checkId() {
  let memberId = $('#vMemberId').val();
  console.log("vMemberId" , memberId);

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
        alert('이미 사용중인 아이디입니다.\n 다른 아이디를 입력하세요');
      } else {
        alert('사용 가능한 아이디입니다.');
      }
    },
    error: function (xhr, status, error) {
      console.error('Error:', error);
    }
  })
}




//총관리자가 회원 조회 시 이름, 이메일, 아이디, 권한별 회원 조회
function filterMembers() {

  member.vMemberNm = $('#name').val() === '' ? null : $('#name').val();
  member.vEmail = $('#email').val()=== '' ? null :$('#email').val();
  member.vMemberId = $('#id').val()=== '' ? null :$('#id').val();

  member.vMemberAuth = changeMemberAuth();

  // AJAX 요청
  $.ajax({
    url: '/admin/namefilter',
    type: 'POST',
    data: JSON.stringify(member),
    contentType: 'application/json',
    success: function (data) {

      console.log("조회 결과 ", JSON.stringify(member))

      let tableBody = $('.zero-configuration tbody');
      tableBody.empty(); // 기존 테이블 내용 초기화

      $.each(data, function (i, members) {

        let row = `<tr>
                        <td>${members.pkMemberSeq}</td>
                        <td>${members.vMemberNm}</td>
                        <td>${members.vMemberId}</td>
                        <td>${members.vEmail}</td>
                        <td>${members.vMemberAuth}</td>
                        <td>
                        <input type="button" class="btn btn-primary ssgssag-blue"
                               value="수정"
                               data-toggle="modal"
                               data-target="#customAlert"
                               vMemberId="${members.vMemberId}"
                               onclick="modalButton(this.getAttribute('vMemberId'))"/>
                        
                        </td>
                        <td>
                        <form>
                          <input type="button" class="btn btn-primary ssgssag-blue" th:value="삭제"/>
                        </form></td>
                        </tr>`;
        tableBody.append(row);

      });
    },
    error: function(xhr, status, error) {
      console.error('AJAX 요청 에러:', error);
    }
  });
}



//총관리자 회원 조회 시 개인정보 수정 모달 창
function modalButton(memberId) {
  console.log("current ", memberId);

  $("#editModal").modal('show');

  $.ajax({
    url: `/admin/get-one-member?memberId=${memberId}`,
    type: 'GET',
    contentType: 'application/json',
    success: function (getone) {
      console.log("조회 결과 ", JSON.stringify(getone))

      let tableBody = $('.modalForm tbody');
      tableBody.empty(); // 기존 테이블 내용 초기화

      $('#editModal #memberId').val(getone.vmemberId);
      $('#editModal #memberName').val(getone.vmemberNm);
      $('#editModal #memberPw').val(getone.vmemberPw);
      $('#editModal #memberEmail').val(getone.vemail);
      $('#editModal #memberRole').val(getone.vmemberAuth);

      let memberAuth = null;

      switch(getone.vmemberAuth) {
        case 'OPERATOR':
          memberAuth = 'operator';
          break;
        case 'WAREHOUSE_MANAGER':
          memberAuth = 'warehouse';
          break;
        default:
          memberAuth = 'admin';
      }
      console.log("권한", memberAuth);


      $('#editModal #memberRole').find('option[value="' + memberAuth + '"]').prop('selected', true);

      $("#editModal").modal('show');

    },
    error: function (xhr, status, error) {
      console.error('AJAX 요청 에러:', error);
    }
  });
}

function signupRequest() {
  let member = {
    "vMemberNm" : $('#vMemberNm').val(),
    "vMemberId" : $('#vMemberId').val(),
    "vMemberPw" : $('#vMemberPw').val(),
    "vEmail": $('#vEmail').val()
  }

  // let member = {
  //   "vMemberNm" : "최소원",
  //   "vMemberId" : "test0325",
  //   "vMemberPw" : "0325",
  //   "vEmail": "test0325@naver.com"
  // }

  console.log(member);

  $.ajax({
    // url: '/member/signup',
    url: '/member/signup',
    type: 'POST',
    contentType:'application/json',
    data: JSON.stringify(member),
    success: function (response) {
      // 성공 시 처리 로직 작성
      window.location.href='/'
      alert('회원가입이 완료되었습니다.');
    },
    error: function (xhr, status, error) {
      // 에러 처리 로직 작성
      console.error('Error:', error);
      alert('회원가입에 실패하였습니다. 다시 시도해주세요.');
    }
  });
}
