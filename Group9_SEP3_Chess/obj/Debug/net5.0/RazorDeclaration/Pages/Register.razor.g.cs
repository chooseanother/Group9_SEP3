// <auto-generated/>
#pragma warning disable 1591
#pragma warning disable 0414
#pragma warning disable 0649
#pragma warning disable 0169

namespace Group9_SEP3_Chess.Pages
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
#nullable restore
#line 1 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using System.Net.Http;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Microsoft.AspNetCore.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 3 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Microsoft.AspNetCore.Components.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 4 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Microsoft.AspNetCore.Components.Forms;

#line default
#line hidden
#nullable disable
#nullable restore
#line 5 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Microsoft.AspNetCore.Components.Routing;

#line default
#line hidden
#nullable disable
#nullable restore
#line 6 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Microsoft.AspNetCore.Components.Web;

#line default
#line hidden
#nullable disable
#nullable restore
#line 7 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Microsoft.AspNetCore.Components.Web.Virtualization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 8 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Microsoft.JSInterop;

#line default
#line hidden
#nullable disable
#nullable restore
#line 9 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Group9_SEP3_Chess;

#line default
#line hidden
#nullable disable
#nullable restore
#line 10 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/_Imports.razor"
using Group9_SEP3_Chess.Shared;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/Pages/Register.razor"
using Microsoft.AspNetCore.Components;

#line default
#line hidden
#nullable disable
#nullable restore
#line 3 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/Pages/Register.razor"
using Group9_SEP3_Chess.Models;

#line default
#line hidden
#nullable disable
#nullable restore
#line 4 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/Pages/Register.razor"
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
        }
        #pragma warning restore 1998
#nullable restore
#line 47 "/home/gimpe/Nextcloud/School/RiderProjects/SEP3/Group9_SEP3_Chess/Group9_SEP3_Chess/Pages/Register.razor"
       
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