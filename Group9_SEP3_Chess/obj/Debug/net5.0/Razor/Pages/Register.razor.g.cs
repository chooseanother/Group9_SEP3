#pragma checksum "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "638c44b64b58b1890fc5cff9ea57f8ea2117ee60"
// <auto-generated/>
#pragma warning disable 1591
namespace Group9_SEP3_Chess.Pages
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
#nullable restore
#line 1 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using System.Net.Http;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 3 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 4 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Forms;

#line default
#line hidden
#nullable disable
#nullable restore
#line 5 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Routing;

#line default
#line hidden
#nullable disable
#nullable restore
#line 6 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Web;

#line default
#line hidden
#nullable disable
#nullable restore
#line 7 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.AspNetCore.Components.Web.Virtualization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 8 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Microsoft.JSInterop;

#line default
#line hidden
#nullable disable
#nullable restore
#line 9 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Group9_SEP3_Chess;

#line default
#line hidden
#nullable disable
#nullable restore
#line 10 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\_Imports.razor"
using Group9_SEP3_Chess.Shared;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
using Microsoft.AspNetCore.Components;

#line default
#line hidden
#nullable disable
#nullable restore
#line 3 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
using Group9_SEP3_Chess.Models;

#line default
#line hidden
#nullable disable
#nullable restore
#line 4 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
using Group9_SEP3_Chess.Data;

#line default
#line hidden
#nullable disable
    [Microsoft.AspNetCore.Components.RouteAttribute("/Register")]
    public partial class Register : Microsoft.AspNetCore.Components.ComponentBase
    {
        #pragma warning disable 1998
        protected override void BuildRenderTree(Microsoft.AspNetCore.Components.Rendering.RenderTreeBuilder __builder)
        {
            __builder.AddMarkupContent(0, "<h3>Register</h3>\r\n");
            __builder.OpenComponent<Microsoft.AspNetCore.Components.Forms.EditForm>(1);
            __builder.AddAttribute(2, "Model", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.Object>(
#nullable restore
#line 9 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                  newUserItem

#line default
#line hidden
#nullable disable
            ));
            __builder.AddAttribute(3, "OnValidSubmit", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback<Microsoft.AspNetCore.Components.Forms.EditContext>>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create<Microsoft.AspNetCore.Components.Forms.EditContext>(this, 
#nullable restore
#line 9 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                                               HandleValidSubmit

#line default
#line hidden
#nullable disable
            )));
            __builder.AddAttribute(4, "OnInvalidSubmit", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback<Microsoft.AspNetCore.Components.Forms.EditContext>>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create<Microsoft.AspNetCore.Components.Forms.EditContext>(this, 
#nullable restore
#line 9 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                                                                                    HandleInvalidSubmit

#line default
#line hidden
#nullable disable
            )));
            __builder.AddAttribute(5, "ChildContent", (Microsoft.AspNetCore.Components.RenderFragment<Microsoft.AspNetCore.Components.Forms.EditContext>)((context) => (__builder2) => {
                __builder2.OpenElement(6, "div");
                __builder2.AddAttribute(7, "class", "row");
                __builder2.OpenElement(8, "div");
                __builder2.AddAttribute(9, "class", "col-9");
                __builder2.OpenComponent<Microsoft.AspNetCore.Components.Forms.DataAnnotationsValidator>(10);
                __builder2.CloseComponent();
                __builder2.AddMarkupContent(11, "\r\n            ");
                __builder2.OpenComponent<Microsoft.AspNetCore.Components.Forms.ValidationSummary>(12);
                __builder2.CloseComponent();
                __builder2.AddMarkupContent(13, "\r\n            ");
                __builder2.OpenElement(14, "div");
                __builder2.AddAttribute(15, "style", "display: grid;grid-template-columns: auto auto");
                __builder2.AddAttribute(16, "data-role", "listview");
                __builder2.OpenElement(17, "div");
                __builder2.AddAttribute(18, "class", "form-group");
                __builder2.AddMarkupContent(19, "\r\n                    Username:<br>\r\n                    ");
                __builder2.OpenComponent<Microsoft.AspNetCore.Components.Forms.InputText>(20);
                __builder2.AddAttribute(21, "Value", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 17 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                                            newUserItem.Username

#line default
#line hidden
#nullable disable
                ));
                __builder2.AddAttribute(22, "ValueChanged", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback<System.String>>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create<System.String>(this, Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.CreateInferredEventCallback(this, __value => newUserItem.Username = __value, newUserItem.Username))));
                __builder2.AddAttribute(23, "ValueExpression", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.Linq.Expressions.Expression<System.Func<System.String>>>(() => newUserItem.Username));
                __builder2.CloseComponent();
                __builder2.CloseElement();
                __builder2.AddMarkupContent(24, "\r\n                <div></div>\r\n                ");
                __builder2.OpenElement(25, "div");
                __builder2.AddAttribute(26, "class", "form-group");
                __builder2.AddMarkupContent(27, "\r\n                    Email:<br>\r\n                    ");
                __builder2.OpenComponent<Microsoft.AspNetCore.Components.Forms.InputText>(28);
                __builder2.AddAttribute(29, "Value", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 24 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                                            newUserItem.Email

#line default
#line hidden
#nullable disable
                ));
                __builder2.AddAttribute(30, "ValueChanged", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback<System.String>>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create<System.String>(this, Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.CreateInferredEventCallback(this, __value => newUserItem.Email = __value, newUserItem.Email))));
                __builder2.AddAttribute(31, "ValueExpression", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.Linq.Expressions.Expression<System.Func<System.String>>>(() => newUserItem.Email));
                __builder2.CloseComponent();
                __builder2.CloseElement();
                __builder2.AddMarkupContent(32, "\r\n                ");
                __builder2.OpenElement(33, "div");
                __builder2.AddAttribute(34, "class", "form-group");
                __builder2.AddMarkupContent(35, "\r\n                    Confirm Email:<br>\r\n                    ");
                __builder2.OpenComponent<Microsoft.AspNetCore.Components.Forms.InputText>(36);
                __builder2.AddAttribute(37, "Value", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 28 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                                            newUserItem.ConfirmEmail

#line default
#line hidden
#nullable disable
                ));
                __builder2.AddAttribute(38, "ValueChanged", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback<System.String>>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create<System.String>(this, Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.CreateInferredEventCallback(this, __value => newUserItem.ConfirmEmail = __value, newUserItem.ConfirmEmail))));
                __builder2.AddAttribute(39, "ValueExpression", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.Linq.Expressions.Expression<System.Func<System.String>>>(() => newUserItem.ConfirmEmail));
                __builder2.CloseComponent();
                __builder2.CloseElement();
                __builder2.AddMarkupContent(40, "\r\n                ");
                __builder2.OpenElement(41, "div");
                __builder2.AddAttribute(42, "class", "form-group");
                __builder2.AddMarkupContent(43, "\r\n                    Password:<br>\r\n                    ");
                __builder2.OpenComponent<Microsoft.AspNetCore.Components.Forms.InputText>(44);
                __builder2.AddAttribute(45, "type", "password");
                __builder2.AddAttribute(46, "Value", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 32 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                                                            newUserItem.Password

#line default
#line hidden
#nullable disable
                ));
                __builder2.AddAttribute(47, "ValueChanged", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback<System.String>>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create<System.String>(this, Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.CreateInferredEventCallback(this, __value => newUserItem.Password = __value, newUserItem.Password))));
                __builder2.AddAttribute(48, "ValueExpression", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.Linq.Expressions.Expression<System.Func<System.String>>>(() => newUserItem.Password));
                __builder2.CloseComponent();
                __builder2.CloseElement();
                __builder2.AddMarkupContent(49, "\r\n                ");
                __builder2.OpenElement(50, "div");
                __builder2.AddAttribute(51, "class", "form-group");
                __builder2.AddMarkupContent(52, "\r\n                    Confirm Password:<br>\r\n                    ");
                __builder2.OpenComponent<Microsoft.AspNetCore.Components.Forms.InputText>(53);
                __builder2.AddAttribute(54, "type", "password");
                __builder2.AddAttribute(55, "Value", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.String>(
#nullable restore
#line 36 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                                                            newUserItem.ConfirmPassword

#line default
#line hidden
#nullable disable
                ));
                __builder2.AddAttribute(56, "ValueChanged", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<Microsoft.AspNetCore.Components.EventCallback<System.String>>(Microsoft.AspNetCore.Components.EventCallback.Factory.Create<System.String>(this, Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.CreateInferredEventCallback(this, __value => newUserItem.ConfirmPassword = __value, newUserItem.ConfirmPassword))));
                __builder2.AddAttribute(57, "ValueExpression", Microsoft.AspNetCore.Components.CompilerServices.RuntimeHelpers.TypeCheck<System.Linq.Expressions.Expression<System.Func<System.String>>>(() => newUserItem.ConfirmPassword));
                __builder2.CloseComponent();
                __builder2.CloseElement();
                __builder2.CloseElement();
                __builder2.AddMarkupContent(58, "\r\n            ");
                __builder2.AddMarkupContent(59, "<p class=\"actions\"><button class=\"btn btn-dark\" type=\"submit\">Register</button></p>");
                __builder2.CloseElement();
                __builder2.CloseElement();
            }
            ));
            __builder.CloseComponent();
            __builder.AddMarkupContent(60, "\r\n");
            __builder.OpenElement(61, "p");
            __builder.AddAttribute(62, "id", "result");
            __builder.AddContent(63, 
#nullable restore
#line 45 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
                result

#line default
#line hidden
#nullable disable
            );
            __builder.CloseElement();
        }
        #pragma warning restore 1998
#nullable restore
#line 47 "D:\CSharp\Group9_SEP3\Group9_SEP3_Chess\Pages\Register.razor"
       
    private User newUserItem = new ();
    private string result;

    private async Task HandleValidSubmit()
    {
        Console.WriteLine(newUserItem);
        try
        {
            result = (await _userService.RegisterUserAsync(new Message{Action = "Register",Username = newUserItem.Username,Email = newUserItem.Email,Password = newUserItem.Password})).Action;
            // result = await _userService.CallAsync($"{new Random().Next(40)}");
        }
        catch (Exception e)
        {
            result = e.Message;
        }
        finally
        {
            
        }
    }
    
    private void HandleInvalidSubmit()
    {
        result = "";
    }

#line default
#line hidden
#nullable disable
        [global::Microsoft.AspNetCore.Components.InjectAttribute] private IUserService _userService { get; set; }
    }
}
#pragma warning restore 1591
