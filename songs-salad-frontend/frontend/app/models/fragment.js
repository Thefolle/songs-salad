import Model, { attr, belongsTo } from '@ember-data/model';

export default class FragmentModel extends Model {
//  @belongsTo('song') song;
  @attr('number') position;
  @attr('string') text;
  @attr('boolean') isChorus;
}
