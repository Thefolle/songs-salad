import Model, { attr, belongsTo } from '@ember-data/model';

export default class SongModel extends Model {
  @attr('string') text;
  @attr('string') title;
  @attr phases;
  @belongsTo('sheet') sheet;
}
