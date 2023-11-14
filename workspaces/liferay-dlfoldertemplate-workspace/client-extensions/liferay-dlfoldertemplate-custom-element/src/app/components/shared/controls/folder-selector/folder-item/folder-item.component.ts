import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DynamicFlatNode} from "../../../../../services/dynamic/folder-service.service";
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-folder-item',
  templateUrl: './folder-item.component.html',
  styleUrls: ['./folder-item.component.css'],
  animations: [
    trigger('showHide', [
      state('show', style({ opacity: 1 })),
      state('hide', style({ opacity: 0, display: 'none' })),
      transition('show => hide', animate('300ms ease-out')),
      transition('hide => show', animate('300ms ease-in')),
    ]),
  ],
})
export class FolderItemComponent implements OnInit{
  @Input('node')
  public node:DynamicFlatNode | any;
  @Input('ref')
  dialogRef:any;
  @Output('select') functionEmitter: EventEmitter<any> = new EventEmitter<any>();
  isListVisible = false;

  toggleListVisibility() {
    this.isListVisible = !this.isListVisible;
  }
  constructor(private cdr: ChangeDetectorRef) {
  }
  ngOnInit(): void {
    try {
     this.hasChildren = this.node.expandable;
     if (this.hasChildren)
     {
       this.loadChilds();
     }
    }catch (exp:any)
    {
      console.log(exp.message);
    }
  }
  children:Array<DynamicFlatNode> = [];
  hasChildren : boolean = false;
  async loadChilds()
  {
    if (this.node.expandable)
    {
      this.node.children = await this.node.getChildren();
      this.children = this.node.children;
      this.cdr.detectChanges();
    }
  }
  select(node:any)
  {
    this.dialogRef?.close({selected:true,data:node});
  }

  select1(node:any) {
    console.log(node);
    if (node)
    {
      this.functionEmitter.emit(node);
    }
    else
    {
      this.functionEmitter.emit(this.node);
    }

  }
}
