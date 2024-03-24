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
        window.location.href='/member/memberslist'
        alert('회원가입이 완료되었습니다.');
      },
      error: function (xhr, status, error) {
        // 에러 처리 로직 작성
        console.error('Error:', error);
        alert('회원가입에 실패하였습니다. 다시 시도해주세요.');
      }
    });
  });



  $("#save-modify").submit(function (e) {
    e.preventDefault();

    var memberId = $('#memberId').val();
    var memberPw = $('#memberPw').val();
    var memberName = $('#memberName').val();
    var memberEmail = $('#memberEmail').val();
    var role = $('#memberRole').val();
    var memberAuth;

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
      url: '/member/modifymemberInfo',
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
        window.location.href='/member/memberslist'
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
      memberAuth = 'ALL'; break;
  }

  return memberAuth;
}



//
function filterMembers() {

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
                        <td><input type="button" class="btn btn-primary btn-modify" th:value="수정" onclick = "modalPage()"></button></td>
                        <td><input type="button" class="btn btn-primary" th:value="삭제"></button></td>
                        </tr>`;
        tableBody.append(row);

      });
    },
    error: function(xhr, status, error) {
      console.error('AJAX 요청 에러:', error);
    }
  });
}

function modalButton(memberId) {
    console.log("current ", memberId);

    $("#editModal").modal('show');

    $.ajax({
      url: `/member/getOneMember?memberId=${memberId}`,
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


//
// function modalPage(){
//   // 수정 버튼 클릭 시 모달 띄우기
//     $.ajax({
//       url: '/member/getOneMember',
//       type: 'POST',
//       data: JSON.stringify(member),
//       contentType: 'application/json',
//       success: function (data) {
//
//         console.log("조회 결과 ", JSON.stringify(member))
//
//         let tableBody = $('.modalForm tbody');
//         tableBody.empty(); // 기존 테이블 내용 초기화
//
//         $.each(data, function (i, getone) {
//
//
//           $('#editModal #memberId').val(${getone.vMemberId});
//           $('#editModal #memberName').val(${getone.vMemberNm});
//           $('#editModal #memberPw').val(${getone.vMemberPw});
//           $('#editModal #memberEmail').val(${getone.vEmail});
//           $('#editModal #memberRole').val(${getone.vEmail});
//         });
//       },
//       error: function (xhr, status, error) {
//         console.error('AJAX 요청 에러:', error);
//       }
//     });
//   };
//
//
//
//
//
//
// //
// // // 모달에 회원 정보 채우기
// //     $('#editModal #memberId').val(memberId);
// //     $('#editModal #memberName').val(memberName);
// //     $('#editModal #memberEmail').val(memberEmail);
// //     $('#editModal #memberRole').val(memberRole);
// //
// //     // 모달 띄우기
// //     $('#editModal').modal('show');
// //   });
// //   });
// // //
// // //   // 저장 버튼 클릭 시 AJAX 요청
// // //   $('#saveBtn').click(function() {
// // //     var memberId = $('#memberId').val();
// // //     var memberName = $('#memberName').val();
// // //     var memberEmail = $('#memberEmail').val();
// // //     var memberRole = $('#memberRole').val();
// // //
// // //     $.ajax({
// //       type: 'POST',
// //       url: '/member/ㅎㄷ',
// //       data: {
// //         memberId: memberId,
// //         memberName: memberName,
// //         memberEmail: memberEmail,
// //         memberRole: memberRole
// //       },
// //       success: function(response) {
// //         // 성공 시 모달 닫기 및 페이지 새로고침
// //         $('#editModal').modal('hide');
// //         location.reload();
// //       },
// //       error: function(xhr, status, error) {
// //         // 실패 시 에러 처리
// //         console.error(xhr.responseText);
// //       }
// //     });
// //   });
// });