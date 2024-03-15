;!(function() {
  multipleTextInput = (inputId, _options) => {
    // 各種設定
    let _defaults = {
      text_input: {
        class: [],
      },
      close_btn: {
        label: 'x',
        class: [],
      }
    }
    let defaults = _deepClone(_defaults, _options)
    console.dir(defaults)

    // 選択された値の配列
    let selectValues = [];

    // データ送信用入力欄
    let mainInput = document.getElementById(inputId)
    mainInput.style.display = "none"
    mainInput.style.visibility = 'hidden'
    if (mainInput.value != "")  {
      selectValues = mainInput.value.split(",")
    }

    let selectArea = document.createElement("div")
    selectArea.classList.add("select-area")

    // 表示用入力欄
    let subInput = document.createElement("input")
    subInput.classList = mainInput.classList
    subInput.classList.add("select-input")
    defaults.text_input.class.forEach(className => {
      subInput.classList.add(className)
    });
  subInput.setAttribute("list", mainInput.getAttribute("list"))
    subInput.addEventListener("change", (e) => _subInput(e))

    // 表示要素の追加
    mainInput.parentNode.insertBefore(subInput, mainInput.nextElementSibling)
    mainInput.parentNode.insertBefore(selectArea, subInput.nextElementSibling)

    // オプション更新
    function _deepClone(_defaults, _overrides = {}) {
      let defaults = {}
      // 入力欄の更新
      if ('text_input' in _overrides) {
        defaults['text_input'] = Object.assign({}, _defaults.text_input, _overrides.text_input)
      } else {
        defaults['text_input'] = _defaults.text_input
      }

      // closeボタン系の更新
      if ('close_btn' in _overrides) {
        defaults['close_btn'] = Object.assign({}, _defaults.close_btn, _overrides.close_btn)
      } else {
        defaults['close_btn'] = _defaults.close_btn
      }
      return defaults
    }

    // テキストの追加アクション
    const _subInput = (e) => {
      let value = e.target.value
      e.target.value = ""

      let areaTag = document.createElement("div")
      areaTag.classList.add("select-area-tag")

      let closeBtn = document.createElement("a")
      closeBtn.href = "#"
      closeBtn.innerHTML = defaults.close_btn.label
      console.log(defaults.close_btn, typeof defaults.close_btn.class)
      defaults.close_btn.class.forEach(className => {
        closeBtn.classList.add(className)
      });
      closeBtn.addEventListener("click",(e) => _closeBtn(e))
      areaTag.appendChild(closeBtn)

      let areaSapn = document.createElement("span")
      areaSapn.innerHTML = value
      areaTag.appendChild(areaSapn)

      selectArea.appendChild(areaTag)
      selectValues.push(value)

      mainInput.value = selectValues.join(",")
    }
    // 閉じるボタンのアクション
    const _closeBtn = (e) => {
      e.preventDefault()

      // 削除するテキスト
      let deleteText = e.target.closest('.select-area').querySelector('a + span').innerHTML
      console.log(selectValues, deleteText, selectValues.indexOf(deleteText))
      selectValues = selectValues.filter(text => text !== deleteText)
      mainInput.value = selectValues.join(",")
      console.log(selectValues)

      // closeボタンが押されたdomを削除
      e.target.closest('.select-area').removeChild(e.target.closest('.select-area-tag'))
      return false
    }
  }
})();
