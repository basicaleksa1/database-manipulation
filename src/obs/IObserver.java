package obs;

public interface IObserver {
	void addListener(IListener listener);
	void removeListener(IListener listener);
	void notifyListeners(Object event);
}
