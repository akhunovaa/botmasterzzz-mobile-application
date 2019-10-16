package com.botmasterzzz.mobile.application.navigation.items;

import android.view.View;

import com.botmasterzzz.mobile.application.MainContext;
import com.botmasterzzz.mobile.application.about.AboutFragment;
import com.botmasterzzz.mobile.application.login.LoginFragment;
import com.botmasterzzz.mobile.application.settings.SettingsFragment;
import com.botmasterzzz.mobile.application.wifi.accesspoint.AccessPointsFragment;
import com.botmasterzzz.mobile.application.wifi.channelavailable.ChannelAvailableFragment;
import com.botmasterzzz.mobile.application.wifi.channelgraph.ChannelGraphFragment;
import com.botmasterzzz.mobile.application.wifi.channelrating.ChannelRatingFragment;
import com.botmasterzzz.mobile.application.wifi.timegraph.TimeGraphFragment;
import com.botmasterzzz.mobile.util.UserUtil;

public class NavigationItemFactory {
    public static final NavigationItem ACCESS_POINTS = new FragmentItem(new AccessPointsFragment());
    public static final NavigationItem CHANNEL_RATING = new FragmentItem(new ChannelRatingFragment());
    public static final NavigationItem CHANNEL_GRAPH = new FragmentItem(new ChannelGraphFragment());
    public static final NavigationItem TIME_GRAPH = new FragmentItem(new TimeGraphFragment());
    public static final NavigationItem EXPORT = new ExportItem();
    public static final NavigationItem CHANNEL_AVAILABLE = new FragmentItem(new ChannelAvailableFragment(), false);
    public static final NavigationItem SETTINGS = new FragmentItem(new SettingsFragment(), false, View.GONE);
    public static final NavigationItem LOGIN = new FragmentItem(new LoginFragment());
    public static final NavigationItem ABOUT = new FragmentItem(new AboutFragment(), false, View.GONE);

    private NavigationItemFactory() {
        throw new IllegalStateException("Factory class");
    }
}
