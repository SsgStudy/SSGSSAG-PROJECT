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

  // 아이디 중복 확인 버튼 클릭 시 alert 창 생성
  $("#alertButton").click(function () {
    alert("중복된 아이디입니다.\n다른 아이디를 입력해주세요");
  });

  // 회원 가입 폼 제출 시 Ajax 요청 보내기
  $("#signup-form").submit(function (e) {
    e.preventDefault();

    var formData = $(this).serialize(); // 폼 데이터 직렬화

    $.ajax({
      url: '/member/signup',
      type: 'POST',
      data: formData,
      success: function (response) {
        // 성공 시 처리 로직 작성
        console.log(response);
        window.location.href="='/member/memberslist"
        alert('회원가입이 완료되었습니다.');
        // 원하는 곳으로 리다이렉트 등의 추가 처리 가능
      },
      error: function (xhr, status, error) {
        // 에러 처리 로직 작성
        console.error('Error:', error);
        alert('회원가입에 실패하였습니다. 다시 시도해주세요.');
      }
    });
  });
});


var member = {
  pkMemberSeq:null,
  vMemberId:null,
  vMemberPw:null,
  vMemberNm:null,
  vMemberAuth:null,
  vEmail:null,
  bProfilePic:null,
  vSocialLoginToken:null,
}

function changeMemberAuth() {
  var role = $('#role').val();
  var memberAuth;

  switch(role) {
    case 'admin':
      memberAuth = 'ADMIN'; break;
    case 'warehouse':
      memberAuth = 'WAREHOUSE_MANAGER'; break;
    case 'operator':
      memberAuth = 'OPERATOR';
    default:
      memberAuth = ''; break;
  }


  return memberAuth;
}



function fetchMembersByName() {

  member.vMemberNm = $('#name').val() === '' ? null : $('#name').val();
  member.vEmail = $('#email').val()=== '' ? null :$('#email').val();
  member.vMemberId = $('#id').val()=== '' ? null :$('#id').val();
  member.vMemberAuth = changeMemberAuth();

  // AJAX 요청
  $.ajax({
    url: '/member/namefilter',
    type: 'POST',
    data: JSON.stringify(member),
    contentType: 'application/json',
    success: function (data) {

      console.log("조회 결과 ", JSON.stringify(member))

      let tableBody = $('.zero-configuration tbody');
      tableBody.empty(); // 기존 테이블 내용 초기화

      $.each(data, function (i, members) {

        console.log(members);

          let row = `<tr>
                        <td>${members.pkMemberSeq}</td>
                        <td>${members.vMemberNm}</td>
                        <td>${members.vMemberId}</td>
                        <td>${members.vEmail}</td>
                        <td>${members.vMemberAuth}</td>
                        <td><input type="button" class="btn btn-primary" th:value="수정"></td>
                        <td><input type="button" class="btn btn-primary" th:value="삭제"></td>
                        </tr>`;
          tableBody.append(row);

      });
    },
    error: function(xhr, status, error) {
      console.error('AJAX 요청 에러:', error);
    }
  });
}


// function fetchMembersByName() {
//   var url = '/member/namefilter'; // AJAX 요청을 보낼 URL
//   var formData = {
//     name: $("input[name='name']").val()
//   };
//   // AJAX 요청
//   $.ajax({
//     url: url,
//     type: 'POST',
//     data: $.param(formData),
//     contentType: 'application/x-www-form-urlencoded',
//     success: function (data) {
//       let tableBody = $('.zero-configuration tbody');
//       tableBody.empty(); // 기존 테이블 내용 초기화
//       // 응답 데이터를 사용하여 테이블 행 생성 및 추가
//       $.each(data, function (i, member) {
//         var row = '<tr>';
//         row += '<td>' + member.pkMemberSeq + '</td>';
//         row += '<td>' + member.vmemberNm + '</td>'; // 필드 이름 변경
//         row += '<td>' + member.vmemberId + '</td>'; // 필드 이름 변경
//         row += '<td>' + member.vemail + '</td>'; // 필드 이름 변경
//         row += '<td>' + member.vmemberAuth + '</td>'; // 필드 이름 변경
//         row += '<td><button class="btn btn-primary">수정</button></td>';
//         row += '<td><button class="btn btn-primary">삭제</button></td>';
//         row += '</tr>';
//         tableBody.append(row);
//       });
//     },
//     error: function(xhr, status, error) {
//       console.error('AJAX 요청 에러:', error);
//     }
//   });
//
// }
