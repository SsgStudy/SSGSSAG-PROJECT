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
  const worstCategoryData = document.getElementById('worstCategoryData').getAttribute('data-worst-category');

  // 문자열을 JSON 객체로 변환합니다.
  const bestCategoryDTOList = JSON.parse(bestCategoryData);
  const worstCategoryDTOList = JSON.parse(worstCategoryData);

  // 문자열을 JSON 객체로 변환합니다.
  const databest = {
    question: "인기 카테고리 별 판매 건수를 제공할거야. 자세한 분석 포인트와 해당 근거, 판매를 높이기 위한 최대한 자세한 제안을 \"analysis\"과 \"suggestion\" 단 두 개의 키 값 존재하는 1차원 JSON 형태로 답변해줘. 분석할 데이터는 다음과 같아."+ JSON.stringify(bestCategoryDTOList)
  };
  const dataWorst = {
    question: "비인기 카테고리 별 판매 건수를 제공할거야. 자세한 분석 포인트와 해당 근거, 판매를 높이기 위한 최대한 자세한 제안을 \"analysis\"과 \"suggestion\" 단 두 개의 키 값 존재하는 1차원 JSON 형태로 답변해줘. 분석할 데이터는 다음과 같아."+ JSON.stringify(worstCategoryDTOList)
  };
  document.getElementById('best-category-analysis-text').innerText = "AI가 인기 카테고리 요인을 열심히 분석하고 있어요~~   (｡ì _ í｡)";
  document.getElementById('best-category-suggestion-text').innerText = "판매율을 높이기 위한 방법을 생각 중이에요!!  (￣◇￣;)";
  document.getElementById('worst-category-analysis-text').innerText = "AI가 비인기 카테고리 요인을 열심히 분석하고 있어요~~   (｡ì _ í｡)";
  document.getElementById('worst-category-suggestion-text').innerText = "재고 소진을 위한 방법을 생각 중이에요!!  (￣◇￣;)";

  fetch('/question', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(databest)
  })
  .then(response => response.json())
  .then(responsebestdata => {
    console.log('Success:', responsebestdata);
    // 응답 데이터를 HTML에 바인딩
    const formattedAnalysis = responsebestdata.analysis.replace(/(\d+\.\s)/g, '\n$1');
    const formattedSuggestion = responsebestdata.suggestion.replace(/(\d+\.\s)/g, '\n$1');
    // 응답 데이터를 HTML에 바인딩
    document.getElementById('best-category-analysis-text').innerText = formattedAnalysis.trim();
    document.getElementById('best-category-suggestion-text').innerText = formattedSuggestion.trim();
  })
  .catch((error) => {
    console.error('Error:', error);
  });

  fetch('/question2', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(dataWorst)
  })
  .then(response => response.json())
  .then(responseworstdata => {
    console.log('Success:', responseworstdata);
    // 응답 데이터를 HTML에 바인딩
    const formattedAnalysis = responseworstdata.analysis.replace(/(\d+\.\s)/g, '\n$1');
    const formattedSuggestion = responseworstdata.suggestion.replace(/(\d+\.\s)/g, '\n$1');
    // 응답 데이터를 HTML에 바인딩
    document.getElementById('worst-category-analysis-text').innerText = formattedAnalysis.trim();
    document.getElementById('worst-category-suggestion-text').innerText = formattedSuggestion.trim();
  })
  .catch((error) => {
    console.error('Error:', error);
  });
});

