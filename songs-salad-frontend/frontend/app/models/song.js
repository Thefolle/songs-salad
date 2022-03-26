import Model, { attr, belongsTo, hasMany } from '@ember-data/model';

export default class SongModel extends Model {
  @attr('string') text;
//  @hasMany('fragment') body; // for now implemented as raw attribute
  @attr body;
  @attr('string') title;
  @attr phases;
  @belongsTo('sheet') sheet;
}
