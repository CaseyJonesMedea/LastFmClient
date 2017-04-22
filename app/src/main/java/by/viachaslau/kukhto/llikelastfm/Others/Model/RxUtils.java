package by.viachaslau.kukhto.llikelastfm.Others.Model;

import rx.Subscription;

/**
 * Created by kuhto on 20.03.2017.
 */

public class RxUtils
{
    public static void unsubscribe(Subscription subscription)
    {
        if (subscription != null)
        {
            if (!subscription.isUnsubscribed())
            {
                subscription.unsubscribe();
            } else
            {
                // Already unsubscribed
            }
        } else
        {
            // Subscription doesn't exist
        }
    }

    public static void unsubscribe(Subscription... subscriptions)
    {
        for (Subscription subscription : subscriptions)
        {
            if (subscription != null)
            {
                if (!subscription.isUnsubscribed())
                {
                    subscription.unsubscribe();
                } else
                {
                    // Already unsubscribed
                }
            } else
            {
                // Subscription doesn't exist
            }
        }
    }
}
