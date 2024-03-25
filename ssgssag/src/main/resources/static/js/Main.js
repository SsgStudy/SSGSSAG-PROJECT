document.addEventListener('DOMContentLoaded', function () {
  const slider = document.querySelector('.scrollable-row-wrapper');
  const flipButton = document.querySelector('#flip-card .btn-dark');
  const backToCategoryChartButton = document.getElementById(
      'back-to-best-category-chart');
  const cardInner = document.querySelector('#flip-card-inner');

  let isDown = false;
  let startX;
  let scrollLeft;

  slider.addEventListener('mousedown', (e
  ) => {
    isDown = true;
    slider.classList.add('active');
    startX = e.pageX - slider.offsetLeft;
    scrollLeft = slider.scrollLeft;
    slider.style.cursor = 'grabbing';
  });

  slider.addEventListener('mouseleave', () => {
    isDown = false;
    slider.style.cursor = 'grab';
  });

  slider.addEventListener('mouseup', () => {
    isDown = false;
    slider.style.cursor = 'grab';
  });

  slider.addEventListener('mousemove', (e) => {
    if (!isDown) {
      return;
    }
    e.preventDefault();
    const x = e.pageX - slider.offsetLeft;
    const walk = (x - startX) * 2;
    slider.scrollLeft = scrollLeft - walk;
  });

  flipButton.addEventListener('click', function () {
    cardInner.classList.toggle('flip'); // 'flip' 클래스 토글로 뒤집기 효과 적용
  });
  backToCategoryChartButton.addEventListener('click', function () {
    cardInner.classList.toggle('flip');
  });

  const flipButtonWorst = document.querySelector(
      '#flip-card-worst .btn-primary');
  const backToWorstCategoryChartButton = document.getElementById(
      'back-to-worst-category-chart');
  const cardInnerWorst = document.querySelector('#flip-card-inner-worst');

  flipButtonWorst.addEventListener('click', function () {
    cardInnerWorst.classList.toggle('flip'); // 'flip' 클래스 토글로 뒤집기 효과 적용
  });
  backToWorstCategoryChartButton.addEventListener('click', function () {
    cardInnerWorst.classList.toggle('flip');
  });
  // 데이터를 포함하는 div 요소에서 데이터를 가져옵니다.
  const bestCategoryData = document.getElementById('bestCategoryData').getAttribute('data-best-category');

  // 문자열을 JSON 객체로 변환합니다.
  const bestCategoryDTOList = JSON.parse(bestCategoryData);

  // 문자열을 JSON 객체로 변환합니다.
  const data = {
    question: "카테고리 별 판매 건수를 제공할거야. 자세한 분석 포인트와 해당 근거, 판매 건수를 늘리거나 재고 관련 최대한 자세한 제안을 Json 형태로 전달해줘. 'analysis'과 'suggestion' 단 두 개의 키 값만 사용하도록 해줘. 단순한 키-밸류 구조를 유지해줘. "+ JSON.stringify(bestCategoryDTOList)
  };
  console.log(bestCategoryDTOList);
  console.log(data);
  document.getElementById('best-category-analysis-text').innerText = "GPT 결과 분석 중";

  // fetch('/question', {
  //   method: 'POST',
  //   headers: {
  //     'Content-Type': 'application/json',
  //   },
  //   body: JSON.stringify(data) // 수정된 부분
  // })
  // .then(response => response.json())
  // .then(data => {
  //   console.log('Success:', data);
  //   document.getElementById('best-category-analysis-text').innerText = data.analysis;
  //   document.getElementById('best-category-suggestion-text').innerText = data.suggestion;
  // })
  // .catch((error) => {
  //   console.error('Error:', error);
  // });
  fetch('/question', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data)
  })
  .then(response => response.json())
  .then(data => {
    console.log('Success:', data);
    // 응답 데이터를 HTML에 바인딩
    document.getElementById('best-category-analysis-text').innerText = data.analysis;
    document.getElementById('best-category-suggestion-text').innerText = data.suggestion;
  })
  .catch((error) => {
    console.error('Error:', error);
  });
});

