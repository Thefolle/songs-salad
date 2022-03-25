import { helper } from '@ember/component/helper';

export default helper(function isNumber([number]) {
  return typeof number === 'number' && isFinite(number);
});
