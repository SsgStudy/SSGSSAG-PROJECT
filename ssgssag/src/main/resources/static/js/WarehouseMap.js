let mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
      level: 3 // 지도의 확대 레벨
    };

let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
let geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다

window.onload = function () {
  // 테이블에서 창고소재지 정보를 추출합니다
  let addresses = Array.from(
      document.querySelectorAll('.warehouseTable tbody tr')).map(
      function (row) {
        return row.cells[3].innerText; // 창고소재지가 4번째 열에 위치
      });

  // 추출한 주소로 마커를 생성합니다
  addresses.forEach(function (address) {
    geocoder.addressSearch(address, function (result, status) {
      if (status === kakao.maps.services.Status.OK) {
        let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        let marker = new kakao.maps.Marker({
          map: map,
          position: coords
        });

        // 지도의 중심을 마지막 마커의 위치로 이동시킵니다 (선택적)
        map.setCenter(coords);
      }
    });
  });
};