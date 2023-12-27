package PrepareExam;
@FunctionalInterface
public interface Calculate<A,B,C> {
    A calculateMethod(B x,C y);
}
