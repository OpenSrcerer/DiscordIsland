package discordIsland.Functions;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.EnumSet;
import java.util.List;

public class PermissionOverride
{
    // checks if user has a PO in List of POs
    public static boolean checkUserOverride(List<net.dv8tion.jda.api.entities.PermissionOverride> li, Member member)
    {
        for (net.dv8tion.jda.api.entities.PermissionOverride it : li)
        {
            if (it.getMember() == member)
                return true;
        }
        return false;
    }

    // checks if user has a permission in a List
    // of permissions provided by a PO

    public static boolean checkUserOverridePermission(EnumSet<Permission> li, Permission pm)
    {
        for (Permission it : li)
        {
            if (it == pm)
                return true;
        }
        return false;
    }
}
