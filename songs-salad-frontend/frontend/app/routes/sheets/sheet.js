import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class SheetsSheetRoute extends Route {
  @service store;

  async model(params) {
    let sheet = await this.store.findRecord('sheet', params.sheet_id);
    let pages = [...new Array(sheet.get('pageNumber')).keys()]; // create array ranging from 0 to pageNumber
    return {
      sheet,
      pages,
    };
  }
}
