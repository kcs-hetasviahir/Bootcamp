import {Component, ViewEncapsulation} from '@angular/core';
import {ITooltipAngularComp} from "ag-grid-angular";

@Component({
    selector: 'tooltip-component',
    template: `
    <a class="tooltips" href="#">
        <span>{{valueToDisplay}}</span>
    </a>
       `,
    styles: [
        `
            :host {
                position: absolute;
                overflow: hidden;
                pointer-events: none;
                transition: opacity 1s;
                width:250px; 
                height:30px;      
            }

            :host.ag-tooltip-hiding {
                opacity: 0;
            }

            .custom-tooltip p {
                margin: 5px;
                white-space: nowrap;
            }

            .custom-tooltip p:first-of-type {
                font-weight: bold;
            }
        `
    ]
})
export class CustomTooltip implements ITooltipAngularComp {

    params: any = '';
    valueToDisplay: string = '';
    isHeader: boolean= false;
    isGroupedHeader: boolean = false;

    agInit(params:any ): void {
        
        this.params = params;
        this.isHeader = params.rowIndex === undefined;
        this.isGroupedHeader = !!params.colDef.children;
        this.valueToDisplay = params.value? params.value : '';
    }
}
