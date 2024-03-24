$(document).ready(function() {
  let menuItems = [
    { name: "통계", url: "/" },
    { name: "발주 입고 등록", url: "/incoming/register" },
    { name: "입고 승인", url: "/incoming/confirm" },
    { name: "입고 수정", url: "/incoming/confirm" },
    { name: "입고 조회", url: "/incoming/list" },
    { name: "출고 조회", url: "/outgoing" },
    { name: "발주 등록", url: "/order/register" },
    { name: "발주 확정", url: "/order/confirm" },
    { name: "발주 조회", url: "/order/read" },
    { name: "창고 조회", url: "/warehouse" },
    { name: "창고 등록", url: "/warehouse" },
    { name: "창고 구역 등록", url: "/warehouse" },
    { name: "재고 현황 조회", url: "/inventory/list" },
    { name: "재고 조정", url: "/inventory/adjustment" },
    { name: "재고 이동", url: "/inventory/movement" },
    { name: "회원 관리", url: "memberslist" },
    { name: "회원 조회", url: "memberslist" }
  ];

  $("#menuSearch").on("input", function() {
    let searchVal = $(this).val().toLowerCase().replace(/\s+/g, '');
    $(".search-results").empty();

    if(searchVal) {
      $(".search-results").show();
      menuItems.forEach(function(item) {
        if(item.name.toLowerCase().replace(/\s+/g, '').includes(searchVal)) {
          $(".search-results").append('<div class="text-left" style="padding: 8px;"><a href="' + item.url + '" style="text-decoration: none; color: black;">' + item.name + '</a></div>');
        }
      });
    } else {
      $(".search-results").hide();
    }
  });
  $("#menuSearch").on("keydown", function(event) {
    if(event.key === "Enter" || event.keyCode === 13) {
      event.preventDefault();
      let firstResultLink = $(".search-results a").first().attr("href");
      if(firstResultLink) window.location.href = firstResultLink;
    }
  });


  $("#menuSearch, .search-results").on("click", function(event) {
    event.stopPropagation();
  });

  $(document).on("click", function() {
    $(".search-results").hide();
  });
});


