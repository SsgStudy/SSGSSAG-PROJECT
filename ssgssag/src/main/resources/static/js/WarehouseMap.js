document.addEventListener('DOMContentLoaded', function() {
  // 창고 리스트 페이지 지도 초기화
  initializeListMap();

  // 모달에서 창고 상세보기를 위한 지도 초기화 이벤트 리스너 추가
  document.querySelectorAll('.btn-warehouse-modal').forEach(button => {
    button.addEventListener('click', function() {
      const address = this.getAttribute('data-warehouse-loc'); // 'this'는 클릭된 버튼을 가리킵니다.
      initializeModalMap(address);
    });
  });
});

function initializeListMap() {
  const mapContainer = document.getElementById('map-in-list'),
      mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
      };

  const map = new kakao.maps.Map(mapContainer, mapOption);
  const geocoder = new kakao.maps.services.Geocoder();

  const addresses = Array.from(document.querySelectorAll('.warehouseTable tbody tr')).map(row => row.cells[3].innerText);

  addresses.forEach(function(address) {
    geocoder.addressSearch(address, function(result, status) {
      if (status === kakao.maps.services.Status.OK) {
        const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        new kakao.maps.Marker({
          map: map,
          position: coords
        });
        map.setCenter(coords);
        // setTimeout(function() { modalMap.relayout(); }, 200);
      }
    });
  });
}

function initializeModalMap(address) {
  const modalMapContainer = document.getElementById('map-in-modal');
  if (!modalMapContainer) return;

  const modalMapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
  };

  let modalMap = new kakao.maps.Map(modalMapContainer, modalMapOption);
  let geocoder = new kakao.maps.services.Geocoder();

  geocoder.addressSearch(address, function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
      let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
      new kakao.maps.Marker({
        map: modalMap,
        position: coords
      });

      // parseFloat()를 사용해 명시적으로 숫자형으로 변환
      let x = parseFloat(result[0].x);
      let y = parseFloat(result[0].y);

      console.log(x)
      console.log(y)

      // 변환된 숫자에 0.001을 더함
      let adjustedX = x - 0.007;
      let adjustedY = y + 0.001; // 예시로, y 값도 조정해봅니다.

      console.log(adjustedX); // 변환 후 결과를 확인하기 위한 로그
      console.log(adjustedY); // 변환 후 결과를 확인하기 위한 로그

      let adjustedCenter = new kakao.maps.LatLng(adjustedY, adjustedX); // 경도 조정 값

      modalMap.setCenter(adjustedCenter);
      setTimeout(function() { modalMap.relayout(); }, 200);  // 지도 레이아웃을 재설정

    } else {
      console.error('Failed to search address:', address);
    }
  });
}