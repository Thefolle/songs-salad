import Model, { attr, belongsTo } from '@ember-data/model';

export default class SheetModel extends Model {
  @belongsTo('song') song;
  @attr('number') pageNumber;
}
