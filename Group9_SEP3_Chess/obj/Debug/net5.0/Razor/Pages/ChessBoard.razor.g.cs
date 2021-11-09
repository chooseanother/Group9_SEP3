#pragma checksum "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "04830983bfdc1a238ca446c3efadd86a811b016e"
// <auto-generated/>
#pragma warning disable 1591
namespace Group9_SEP3_Chess.Pages
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Components;
#nullable restore
#line 1 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using System.Net.Http;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 3 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 4 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Forms;

#line default
#line hidden
#nullable disable
#nullable restore
#line 5 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Routing;

#line default
#line hidden
#nullable disable
#nullable restore
#line 6 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Web;

#line default
#line hidden
#nullable disable
#nullable restore
#line 7 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Web.Virtualization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 8 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.JSInterop;

#line default
#line hidden
#nullable disable
#nullable restore
#line 9 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Group9_SEP3_Chess;

#line default
#line hidden
#nullable disable
#nullable restore
#line 10 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Group9_SEP3_Chess.Shared;

#line default
#line hidden
#nullable disable
    public partial class ChessBoard : Microsoft.AspNetCore.Components.ComponentBase
    {
        #pragma warning disable 1998
        protected override void BuildRenderTree(Microsoft.AspNetCore.Components.Rendering.RenderTreeBuilder __builder)
        {
            __builder.OpenElement(0, "div");
            __builder.AddAttribute(1, "class", "board");
#nullable restore
#line 3 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
     for(int j = 8; j > 0; j--){
        if(j % 2 == 0){
            for (int i = 0; i < 8; i++)
            {
                String StartPice = "";
                String loaction = j + letters[i];
                
                switch (loaction)
                {
                    case "8a": case "8h":
                        StartPice = "Images/BRook.png";
                        break;
                    case "8b": case "8g":
                        StartPice = "Images/BHorse.png";
                        break;
                    case  "8c": case "8f":
                        StartPice = "Images/WBishop.png";
                        break;
                    case  "8d":
                        StartPice = "Images/BQueen.png";
                        break;
                    case  "8e":
                        StartPice = "Images/BKing.png";
                        break;
                    case "2a": case "2b": case "2c": case "2d": case "2e": case "2f": case "2g": case "2h":
                        StartPice = "Images/WPawn.png";
                        break;
                    default:
                        StartPice = null;
                        break;
                }
                
                if (i % 2 == 0)
                {

#line default
#line hidden
#nullable disable
            __builder.OpenComponent<Group9_SEP3_Chess.Pages.BoardSquareBlack>(2);
            __builder.AddAttribute(3, "Image", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 37 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                              StartPice

#line default
#line hidden
#nullable disable
            ));
            __builder.AddAttribute(4, "ClickHandler", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create(this, 
#nullable restore
#line 37 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                                                         () => HandleClick(loaction, StartPice)

#line default
#line hidden
#nullable disable
            )));
            __builder.CloseComponent();
#nullable restore
#line 38 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                }
                else
                {

#line default
#line hidden
#nullable disable
            __builder.OpenComponent<Group9_SEP3_Chess.Pages.BoardSquareWhite>(5);
            __builder.AddAttribute(6, "Image", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 41 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                              StartPice

#line default
#line hidden
#nullable disable
            ));
            __builder.AddAttribute(7, "ClickHandler", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create(this, 
#nullable restore
#line 41 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                                                         () => HandleClick(loaction, StartPice)

#line default
#line hidden
#nullable disable
            )));
            __builder.CloseComponent();
#nullable restore
#line 42 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                }
            }
        }
        else{
            for (int i = 0; i < 8; i++)
            {
                String StartPice = "";
                String loaction = j + letters[i];
                
                switch (loaction)
                {
                    case "1a": case "1h":
                        StartPice = "Images/WRook.png";
                        break;
                    case "1b": case "1g":
                        StartPice = "Images/WHorse.png";
                        break;
                    case "1c": case "1f":
                        StartPice = "Images/WBishop.png";
                        break;
                    case "1d":
                        StartPice = "Images/WQueen.png";
                        break;
                    case "1e":
                        StartPice = "Images/WKing.png";
                        break;
                    case "7a": case "7b": case "7c": case "7d": case "7e": case "7f": case "7g": case "7h":
                        StartPice = "Images/BPawn.png";
                        break;
                    default:
                        StartPice = null;
                        break;    
                        
                }
                
                if (i % 2 == 0)
                {

#line default
#line hidden
#nullable disable
            __builder.OpenComponent<Group9_SEP3_Chess.Pages.BoardSquareWhite>(8);
            __builder.AddAttribute(9, "Image", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 79 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                              StartPice

#line default
#line hidden
#nullable disable
            ));
            __builder.AddAttribute(10, "ClickHandler", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create(this, 
#nullable restore
#line 79 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                                                         () => HandleClick(loaction, StartPice)

#line default
#line hidden
#nullable disable
            )));
            __builder.CloseComponent();
#nullable restore
#line 80 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                }
                else
                {

#line default
#line hidden
#nullable disable
            __builder.OpenComponent<Group9_SEP3_Chess.Pages.BoardSquareBlack>(11);
            __builder.AddAttribute(12, "Image", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 83 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                              StartPice

#line default
#line hidden
#nullable disable
            ));
            __builder.AddAttribute(13, "ClickHandler", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create(this, 
#nullable restore
#line 83 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                                                                         () => HandleClick(loaction, StartPice)

#line default
#line hidden
#nullable disable
            )));
            __builder.CloseComponent();
#nullable restore
#line 84 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
                }
            }
        }}

#line default
#line hidden
#nullable disable
            __builder.CloseElement();
            __builder.AddMarkupContent(14, "\r\n\r\n\r\n");
            __builder.AddMarkupContent(15, @"<style>
    .board {
        display: grid;
        grid-template-columns: auto auto auto auto auto auto auto auto;
        grid-template-rows: auto auto auto auto auto auto auto auto;
        background-color: #da9d1d;
        padding: 10px;
        width: 850px;
        height: 850px;
    }
   
</style>");
        }
        #pragma warning restore 1998
#nullable restore
#line 103 "C:\Users\Nick\RiderProjects\Group9_SEP3\Group9_SEP3_Chess\Pages\ChessBoard.razor"
       
    private String[] letters = new String[8] {"a", "b", "c", "d", "e", "f", "g", "h"};

    private void HandleClick(String loaction, String pice)
    {
        Console.WriteLine("Location: "+ loaction + " Pice: " + pice);
    }


#line default
#line hidden
#nullable disable
    }
}
#pragma warning restore 1591