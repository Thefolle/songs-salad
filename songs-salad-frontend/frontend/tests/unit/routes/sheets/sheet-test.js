import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Route | sheets/sheet', function (hooks) {
  setupTest(hooks);

  test('it exists', function (assert) {
    let route = this.owner.lookup('route:sheets/sheet');
    assert.ok(route);
  });
});
