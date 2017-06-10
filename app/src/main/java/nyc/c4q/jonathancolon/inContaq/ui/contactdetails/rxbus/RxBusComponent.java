package nyc.c4q.jonathancolon.inContaq.ui.contactdetails.rxbus;

import javax.inject.Singleton;

import dagger.Component;
import nyc.c4q.jonathancolon.inContaq.RepositoryModule;
import nyc.c4q.jonathancolon.inContaq.ui.contactdetails.contactsms.ContactSmsFragment;
import nyc.c4q.jonathancolon.inContaq.ui.contactdetails.contactstats.ContactStatsFragment;

@Singleton
@Component(modules = {RxBusModule.class, RepositoryModule.class})
public interface RxBusComponent {
    RxBus getRxBus();

    void inject(ContactStatsFragment fragment);
    void inject(ContactSmsFragment fragment);
}