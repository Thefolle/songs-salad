import Component from '@glimmer/component';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class SongsListComponent extends Component {
  @action
  goToSheetViewer(sheetId, event) {
    console.log(sheetId);
  }
}
