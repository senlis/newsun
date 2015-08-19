package senlis.akka.app.akka_test;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class MyUntypedActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public static void main(String[] args) {

		Props props1 = new Props();
		Props props2 = new Props(MyUntypedActor.class);
		Props props3 = new Props(new UntypedActorFactory() {
			public UntypedActor create() {
				return new MyUntypedActor();
			}
		});
		Props props4 = props1.withCreator(new UntypedActorFactory() {
			public UntypedActor create() {
				return new MyUntypedActor();
			}
		});

		ActorSystem system = ActorSystem.create("MyActorSystem",
				ConfigFactory.load());
		System.out.println(system.settings());
		system.actorOf(props2.withDispatcher("my-dispatcher"), "myactor");

	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			log.info("Receive message string = " + message);
			getContext().become(new Procedure<Object>() {

				@Override
				public void apply(Object arg0) {
					// TODO Auto-generated method stub

				}
			});
		} else {
			unhandled(message);
		}

	}

}
