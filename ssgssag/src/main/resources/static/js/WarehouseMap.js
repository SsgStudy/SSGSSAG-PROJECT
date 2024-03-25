document.addEventListener('DOMContentLoaded', function() {

  initializeListMap();

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
        level: 13
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


      let x = parseFloat(result[0].x);
      let y = parseFloat(result[0].y);

      console.log(x)
      console.log(y)

      // 변환된 숫자에 0.001을 더함
      let adjustedX = x - 0.005;
      let adjustedY = y + 0.002;

      console.log(adjustedX);
      console.log(adjustedY);

      let adjustedCenter = new kakao.maps.LatLng(adjustedY, adjustedX);

      modalMap.setCenter(adjustedCenter);
      setTimeout(function() { modalMap.relayout(); }, 200);

    } else {
      console.error('Failed to search address:', address);
    }
  });
}