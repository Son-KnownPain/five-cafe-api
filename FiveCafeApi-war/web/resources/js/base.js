window.APP_NAME = document.querySelector('meta[name="my-app-name"]').content;
window.currencyOutput = function(value, separate = ".", subfix = "VNĐ") {
    value = (value + "").split('').reverse().join('');
      
    let result = "";
    let j =  1;
    for (let i = 0; i < value.length; i++) {
      if (j === 3) {
          if (i === value.length - 1) {
              result += value[i];
          } else {
              result += value[i] + separate;
              j = 1;
          }
      } else {
          result += value[i];
          j++;
      }
    }

    return `${result.split('').reverse().join('')} ${subfix}`;
}