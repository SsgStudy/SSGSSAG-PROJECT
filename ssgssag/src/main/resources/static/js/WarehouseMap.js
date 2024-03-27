document.addEventListener('DOMContentLoaded', function() {

  initializeListMap();

  document.querySelectorAll('.btn-warehouse-modal').forEach(button => {
    button.addEventListener('click', function() {
      const address = this.getAttribute('data-warehouse-loc');
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


  // 창고 정보(주소 및 이름)를 포함하는 객체 배열 생성
  const warehouses = Array.from(document.querySelectorAll('.warehouseTable tbody tr')).map(row => ({
    address: row.cells[3].innerText,
    name: row.cells[1].innerText
  }));

  warehouses.forEach(function(warehouse) {
    geocoder.addressSearch(warehouse.address, function(result, status) {
      if (status === kakao.maps.services.Status.OK) {
        const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          const marker = new kakao.maps.Marker({
            map: map,
            position: coords,
            clickable: true
          });

          const infowindow = new kakao.maps.InfoWindow({
            content: '<div style="padding:5px;">' + warehouse.name + '</div>',
            removable: true
          });

          kakao.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map, marker);
          });

        let x = parseFloat(result[0].x);
        let y = parseFloat(result[0].y);

        let adjustedX = x - 0.009;
        let adjustedY = y + 0.016;

        let adjustedCenter = new kakao.maps.LatLng(adjustedY, adjustedX);



        map.setCenter(adjustedCenter);
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

      let adjustedX = x - 0.005;
      let adjustedY = y + 0.002;

      let adjustedCenter = new kakao.maps.LatLng(adjustedY, adjustedX);

      modalMap.setCenter(adjustedCenter);
      setTimeout(function() { modalMap.relayout(); }, 200);

    } else {
      console.error('Failed to search address:', address);
    }
  });

}