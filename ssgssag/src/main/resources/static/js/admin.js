const roleToMemberAuth = {
  'admin': 'ADMIN',
  'warehouse': 'WAREHOUSE_MANAGER',
  'operator': 'OPERATOR',
  'default': 'ALL'
};

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


// 회원 수정
$("#save-modifier").submit(function (e) {
  e.preventDefault();

  let memberId = $('#memberId').val();
  let memberPw = $('#memberPw').val();
  let memberName = $('#memberName').val();
  let memberEmail = $('#memberEmail').val();
  let role = $('#memberRole').val();
  let memberAuth = roleToMemberAuth[role];

  $.ajax({
    url: '/admin/member',
    type: 'PATCH',
    data: {
      memberId: memberId,
      memberPw: memberPw,
      memberName: memberName,
      memberEmail: memberEmail,
      memberAuth: memberAuth
    },
    success: function (response) {
      toastr.success('수정 완료');

      setTimeout(function() {
        window.location.href='/admin/list';

      }, 2000);

    },
    error: function (xhr, status, error) {
      // 에러 처리 로직 작성
      console.error('Error:', error);
      toastr.error('수정에 실패하였습니다. 다시 시도해주세요.');
    }
  });
});

//총관리자가 회원 조회 시 이름, 이메일, 아이디, 권한별 회원 조회
function filterMembers() {

  member.vMemberNm = $('#name').val() === '' ? null : $('#name').val();
  member.vEmail = $('#email').val()=== '' ? null :$('#email').val();
  member.vMemberId = $('#id').val()=== '' ? null :$('#id').val();
  let role = $('#role').val();
  member.vMemberAuth = roleToMemberAuth[role];

  // AJAX 요청
  $.ajax({
    url: '/admin/namefilter',
    type: 'POST',
    data: JSON.stringify(member),
    contentType: 'application/json',
    success: function (data) {
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
                                 th:vMemberId="${members.vMemberId}"
                                 th:onclick="'modalButton(' + ${members.vMemberId} + ')'" />
                          </td>
                        <td>
                          <form>
                            <input type="button" class="btn btn-primary ssgssag-blue" value="삭제"/>
                          </form>
                        </td>
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
  $("#editModal").modal('show');

  $.ajax({
    url: `/admin/members/${memberId}/profile`,
    type: 'GET',
    contentType: 'application/json',
    success: function (getone) {
      $('#memberId').val(getone.vMemberId);
      $('#memberName').val(getone.vMemberNm);
      $('#memberPw').val(getone.vMemberPw);
      $('#memberEmail').val(getone.vEmail);
      $('#memberRole').val(getone.vMemberAuth);

      let memberAuth = null;
      switch(getone.vMemberAuth) {
        case 'OPERATOR':
          memberAuth = 'operator';
          break;
        case 'WAREHOUSE_MANAGER':
          memberAuth = 'warehouse';
          break;
        default:
          memberAuth = 'admin';
      }

      $('#memberRole').find('option[value="' + memberAuth + '"]')
                      .prop('selected', true);
      $("#modalForm").modal('show');
    },
    error: function (xhr, status, error) {
      console.error('AJAX 요청 에러:', error);
      toastr.error("회원 정보 불러오기 실패");
    }
  });
}
